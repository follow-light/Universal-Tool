#include <stdio.h>
#include <time.h>
#include "add.h"
#include <stdlib.h>
/**
* 身份证号最后一位校验码计算
*/
int main() {
	int num[] = {0};
	int len = sizeof(num) / sizeof(num[0]);
	printf("len = %d\n", len);
	int sum = 0;
	for (int i = 0; i < len; i++)
	{
		printf("sss = %d i = %d  num[i] = %d\n", (1 << (17 - i)) % 11 * num[i], i , num[i]);
		sum += (1 << (17 - i)) % 11 * num[i];
	}
	printf("sum = %d\n", sum);
	printf("result = %d\n", (12 - (sum % 11)) % 11);
	return 0;
}
