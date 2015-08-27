#!/bin/bash
d=0
w=''
k='zip failedTC.zip '
javac $1 
for ((c = $2; c < $3+1; c++))
do
	echo "Testing TC$c"
	java $(ls | grep $1 | cut -d '.' -f 1) < TC$c.in > output$c
	length=$(diff -b TC$c.out output$c | wc -l)
	if [ $length -eq 0 ]
    then
		((d++))
	else
		w+="$c "
		k+="TC$c.in TC$c.out "
	fi	
done
((total=$3-$2+1))
echo "You have passed $d out of $total test cases."
echo "You have failed the following test cases:"
echo $w
$k
