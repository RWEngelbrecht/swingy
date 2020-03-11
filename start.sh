if [ $# -gt 0 ] && [ $# -lt 2 ]
then 
	echo "starting swingy in $1"
	java -jar target/swingy-1.0-SNAPSHOT.jar $1
else
	echo "run script with gui or console arguments"
fi

