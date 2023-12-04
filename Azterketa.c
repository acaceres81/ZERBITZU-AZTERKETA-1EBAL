#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h> 
#include <signal.h>
#include <sys/wait.h>

void signalHandler(int signal){
    printf("Signal received.\n");
    exit(EXIT_SUCCESS);
}

int main(){
    // create pipes
    int pipe1[2]; // Pipe for communication between Child 1 and Child 2
    int pipe2[2]; // Pipe for communication between Child 2 and Parent
    pid_t pid;
    
    // create the integers that will be printed in the messages
    int totalSum = 0;
    int finalSum;
    
    // create signal handlers
    signal(SIGINT, signalHandler);
    signal(SIGTERM, signalHandler);

    // create pipes
    if (pipe(pipe1) == -1)
    {
        perror("pipe");
        return 1;
    }
    if (pipe(pipe2) == -1)
    {
        perror("pipe");
        return 1;
    }

    pid = fork();

    if (pid < 0)
    {
        perror("fork");
        return 1;
    }
    else if (pid == 0) // Child 1
    {
        close(pipe1[0]); // Close unused read end
        close(pipe2[0]); // Close unused read end
        close(pipe2[1]); // Close unused write end of pipe2
        
        // Send numbers divisible by 7 to Child 2
        for (int i = 1; i <= 5000; i++){
            if((i % 7) == 0){
                write(pipe1[1], &i, sizeof(int));
            }
        }
        printf("Child 1 says: Dividing numbers by seven in the range: [1: 5000] have been sent from child 1 to child 2.\n");

        close(pipe1[1]); // Close write end of pipe1 after sending numbers
    }
    else // Parent
    {
        pid = fork(); // Create Child 2
        if (pid < 0)
        {
            perror("fork");
            return 1;
        }
        else if (pid == 0) // Child 2
        {
            ssize_t bytesRead;
            int receivedNum;
            int count = 0;

            close(pipe1[1]); // Close unused write end
            close(pipe2[0]); // Close unused read end

            // Read numbers from Child 1 and calculate sum
            while ((bytesRead = read(pipe1[0], &receivedNum, sizeof(int))) > 0){
                count++;
                totalSum += receivedNum; // Increment the total sum
            }

            if(bytesRead == -1){
                printf("Child 2 says: Error reading the data (from SECOND child)...\n");
            } else if(bytesRead == 0){
                printf("Child 2 says: All the numbers from child 1 received correctly. A total of %d numbers received\n", count);
            }

            write(pipe2[1], &totalSum, sizeof(int)); // Write total sum to Parent
            printf("Child 2 says: The sum of all numbers calculated and sent from child 2 to parent.\n");

            close(pipe1[0]); // Close read end of pipe1
            close(pipe2[1]); // Close write end of pipe2 after sending the sum
        }
        else // Parent
        {
            close(pipe1[0]); // Close unused read end
            close(pipe1[1]); // Close unused write end
            close(pipe2[1]); // Close unused write end
            
            // Read the final sum calculated by Child 2
            read(pipe2[0], &finalSum, sizeof(int));

            printf("Parent says: The sum of dividing numbers by seven in the range: [1: 5000] is %d\n", finalSum);

            close(pipe2[0]); // Close read end of pipe2
        }
    }

    return 0;
}


