.class public MonteCarloPi
.super java/lang/Object


.method public<init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public performSingleEstimate()I
	.limit stack 99
	.limit locals 99

	istore 1

	istore 2

	iadd
	bipush 100
	istore 4

.end method

.method public estimatePi100(I)I
	.limit stack 99
	.limit locals 99

	bipush 0
	istore 2

	bipush 0
	istore 1

	bipush 400
	istore 3

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	istore 2

	istore 1

	.invokestatic ioPlus/printResult(I)V

.end method


