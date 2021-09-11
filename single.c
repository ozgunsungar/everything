#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#define NO_OF_THREADS 10

struct Singleton {
   char *Data;	
};


struct Singleton *singletonObjectPointer;


int addresses[NO_OF_THREADS];
sem_t sem;

void *runner(void *params); /* the thread */
struct Singleton *getInstance();

int main()
{
	int i;
	sem_init(&sem,0,1);
	pthread_t threadIds[NO_OF_THREADS];


	for (i=0; i < NO_OF_THREADS; i++){
		pthread_create(&threadIds[i], NULL, &runner,(void *)(intptr_t)i);
	} 

	/* Wait until all threads are done */
	for (i=0; i < NO_OF_THREADS; i++){
		pthread_join(threadIds[i], NULL);
	} 

	/* Control addresses. All of them should be same */
	int prevAddr=addresses[0];
	for (i=1; i < NO_OF_THREADS; i++){
		if(addresses[i]!=prevAddr){
			printf("Object is created more than once\n");
			return -1;
		}
		prevAddr=addresses[i];
	}
	for (i=0; i < NO_OF_THREADS; i++){
		printf("Singleton Addresses for each thread %x\n",addresses[i]);
	}
	printf("Successful\n");
	return 1;
}

/**
 * The thread will begin control in this function
 */
void *runner(void *params) 
{
	int i = (intptr_t)params;
	printf("Thread %d\n",i);
	struct Singleton *s = getInstance();
	addresses[i]=(intptr_t)s;
	pthread_exit(0);
}
//Fill this method
struct Singleton *getInstance(){
	//process can be long that's why should be threadsafe
	 if(singletonObjectPointer == NULL){
        	sem_wait(&sem); //working like synchronized keyword in java. Threads join the queue.
			//if we use sem above the first if statement performans loss occurs
			//below code is critical section
        		if (singletonObjectPointer == NULL){ 
            			singletonObjectPointer = (struct Singleton *)malloc(sizeof(struct Singleton)); //After then singletonObject is not null so other threads cannot join the critical section ->
            			printf("---Address of singletonObjectPointer is %x\n",singletonObjectPointer); //-> because of the second if statement
            			singletonObjectPointer->Data="This is object data";
        		}
        	sem_post(&sem);
    	}
	return singletonObjectPointer; // Threads except the thread which joins the critical section directly jump here. And they use same singletonObjectPointer created by first thread.
}