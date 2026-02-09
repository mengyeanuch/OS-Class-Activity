#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    pid_t pid;

    pid = fork();   // create child process

    if (pid < 0) {
        // fork failed
        perror("Fork failed");
        return 1;
    }
    else if (pid == 0) {
        // Child process
        printf("Child process: executing ls command\n");
        execlp("ls", "ls","-l", NULL);

        // If exec fails
        perror("Exec failed");
    }
    else {
        // Parent process
        wait(NULL); // wait for child to finish
        printf("Parent process: child finished execution\n");
    }

    return 0;
}
