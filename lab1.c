#include <stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<unistd.h>
#include<string.h>
#include<ctype.h>
#define MAX_INPUT 256

int main(){//start main
int validity;
char command[MAX_INPUT];//to store users command
const char s[2] =" ";
char copytoken[MAX_INPUT];
char *token;
char *toen;
char **history = (char **) malloc(15 * sizeof(char*));  // character array to store history

int pointer = 0; //pointer the history
//while loop to keep asking user for more inputs
while(1){
	//Q1
	printf("csh>");
	fgets(command,MAX_INPUT,stdin);//take input from user
	//printf("command %s\n",command);
	//memset(strchr(command,'\n'),0,1); 	//part1
	
	//system(command);			//part1
	int atoiint = atoi(command);
								
	if (strcmp(strtok(command, "\n"), "!!") == 0) {		//part3
		if (index == 0) {				//part3
			printf("No previous command\n");	//part3
		} else {					//part3
			strcpy(command, history[pointer-1]);	//part3
		}						//part3
	}							//part3
	if ((atoi(command) != 0 && atoi(command) < pointer) || (strcmp(strtok(command, "\n"), "0") == 0)) {				//part3
		strcpy(command, history[atoi(command)]);	//part3
	}
	history[pointer] = malloc(sizeof(command)); // allocate memory, part3
	strcpy(history[pointer], command);			//part3
	pointer += 1;						//part3
	if (atoi(command) > pointer) {				//part3
		printf("history index out of range\n");		//part3
	}							//part3
	strcpy(copytoken,command);		//part2
	token = strtok(copytoken,s);		//part2
	if (strcmp(token, "cd") == 0){		//part2, token is gone here.
		token = strtok(NULL,s);
		toen = strtok(token,"\n");	//part2
		validity = chdir(toen);		//part2
		
		if (validity == -1){
			printf("error: new directory is invalid!\n");
		}
	}					//part2
//	else if (strcmp(command,"history\n") == 0){ 			//part3
//		for (int i = 0; i < pointer; i = i+1){
//			printf("at [%d] the command is %s \n", i, commandarray[i]);	//part3
//		}							//part3
//	}								//part3
	else if (strcmp(command, "history") == 0) {			//part3
		for (int i=0; i<pointer-1; i++) {			//part3
			printf("at [%d] the command is %s \n", i, history[i]);		//part3
		}							//part3
		pointer -= 1;						//part3
	}								
	else{					//part1
		if (strcmp(strtok(command, "\n"), "!!") != 0) {
			system(command);
		}
	}					//part1


	
/*TO DO LIST:*/
/*
////----------------Case 1, create the external process and execute the command in that process---------------------- ////*/ 



////----------------Case 2, change directory----------------------
//starts with cd
//int chdir(const char*path);
////--------------Case3, History-------------------------
//check if user enteres history option


	}//end while1
}// end main
