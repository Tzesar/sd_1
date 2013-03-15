#include <stdio.h>
#include <stdlib.h>

int main (int argc, char *argv[])
{
	int i, j;
	printf ("%c\n", "no es un caracter");
	if (i=10)
		printf ("fallo\n");
	if (j!=10)
		printf ("otro fallo\n");

	no_decl();

	return (EXIT_SUCCESS);
}
void no_decl (void)
{
	printf ("no_decl\n");
}
