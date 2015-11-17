sed -i 's/^<meta name=\([^" ]*\) /<meta name="\1" /' $1
sed -i 's/content="\([^"]*\)".*>/content="\1">/g' $1
sed -i 's/^<meta.*">$/&<\/meta>/' $1

sed -i 's/&nbsp;//g' $1

sed -i 's/<\/\?\(p\|strong\|a\|code\|cite\|span\|em\|iframe\|div\|br\|img\)[^>]*>//g' $1
sed -i 's/<\/\?b>//g' $1

sed -i 's/&//g' $1
sed -i 's/<\+\([^/<ubmtd]\)/ \1/g' $1

# uncomment next line when parsing 2012_q1
#sed -i '790082d' $1 	

# uncomment next line when parsing 2012_q3
#sed -i '173778d' $1


# uncomment next line when parsing 2012_q3
#sed -i '734133s/...>$/海"\/>/' $1
#sed -i '746482s/..>$/"\/>/' $1
#sed -i '747098d' $1
