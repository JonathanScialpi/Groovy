response = "22.5k one hundred 80 one"

//Cleanup user response and put into array
response = response.toLowerCase().replace("one hundred","100").replace("any","0").replace("-"," ").replaceAll("[^A-Za-z0-9 .]", "").split(" ")

//check if we are dealing with a long or a double
def extractInts( String input ) {
 if(input.contains(".")){
 	input.findAll( /-?\d+\.\d*|-?\d*\.\d+|-?\d+/ )*.toDouble()
 	}else{
 	input.findAll( /-?\d+\.\d*|-?\d*\.\d+|-?\d+/ )*.toLong()
	}
}

try{
magnitudes = ["none":0,"nothing":0,"zero":0,"one":1, "two":2, "three":3, "four":4, "five":5, "six":6, "seven":7, "eight":8, "nine":9,
"ten":10, "twenty":20, "thirty":30, "forty":40,"fifty":50, "sixty":60, "seventy":70, "eighty":80, "ninety":90,
"eleven":11, "twelve":12, "thirteen":13,"fourteen":14, "fifteen":15, "sixteen":16, "seventeen":17, "eighteen":18, "nineteen":19,
"hundred":100,"hundreds":100,"hundo":100,"c-note":100,"benjamin":100,
"thousand":1000,"thousands":1000,"thou":1000,"grand":1000,"k":1000,
"millions":1000000,"million":1000000,"mill":1000000,"mil":1000000,
"billions":1000000000,"billion":1000000000,"bill":1000000000,"bil":1000000000,"half":(1/2)]

number = 0
myList = []
magsFound = []

//Let's first get all the magnitudes and add them to the magsFound List
for(x = 0; x < response.length; x++){
	if(magnitudes.get(response[x]) != null && extractInts(response[x])[0] == null && magnitudes.get(response[x]) >= 100){
		magsFound.add(magnitudes.get(response[x]))
	}else if(magnitudes.get(response[x]) == null && extractInts(response[x])[0] != null && extractInts(response[x])[0] >= 100){
		magsFound.add(extractInts(response[x])[0])
	}
}

//Now let's get all the proper numbers and put them in another list called "myList"
for(x = 0; x < response.length; x++){
	if(extractInts(response[x])[0] != null && response[x].charAt(response[x].size() - 1) == 'k' && extractInts(response[x])[0] < 1000){
		response[x] = extractInts(response[x])[0] * 1000
	}else if(extractInts(response[x])[0] != null && response[x].charAt(response[x].size() - 1) == 'm' && extractInts(response[x])[0] < 1000000){
		response[x] = extractInts(response[x])[0] * 1000000
	}else if(extractInts(response[x])[0] != null && response[x].charAt(response[x].size() - 1) == 'b' && extractInts(response[x])[0] < 1000000000){
		response[x] = extractInts(response[x])[0] * 1000000000
	}

	//number is the current number we are trying to parse
	if(magnitudes.get(response[x]) != null && extractInts(response[x])[0] == null && magnitudes.get(response[x]) < 100 ){
		number += magnitudes.get(response[x])
		if(x == response.length-1 && myList.size() == 0 && magsFound.size() == 0){
			myList.add(number)
		}
		
	}
	else if(magnitudes.get(response[x]) != null && extractInts(response[x])[0] == null && magnitudes.get(response[x]) >= 100){
		if( number != 0){
		number *= magnitudes.get(response[x])
		}else{
		number = magnitudes.get(response[x])
		}	
		myList.add(number)
		number = 0
	}else if(magnitudes.get(response[x]) == null && extractInts(response[x])[0] != null){
		number += extractInts(response[x])[0]
		if(x == response.length-1 && myList.size() == 0 && magsFound.size() == 0){
			myList.add(number)
		}
	}
}

//weirdThingy var is in regard to a bug that occurs in the script if the magsFound and myList lists are equal.
sum = 0
weirdThingy = false

if(magsFound == myList){
	weirdThingy = true
	for (x = 0; x < myList.size(); x++){
		sum += myList[x]
	}
	sum = sum + number
}else if ((magsFound.size == 1 && myList.size() == 1) && magsFound[0] == myList[0]){
	sum = myList[0]
}else if(magsFound.size() > 1 && magsFound != myList){
	for (x = 1; x < myList.size(); x++){
		if(myList[x-1] < myList[x]){
			myList[x-1] = myList[x-1] * magsFound[x]
		}
	}
}

if(weirdThingy == false){
	for(x=0; x< myList.size(); x++){
		if(myList[x] != magsFound[x]){
		sum+= myList[x]
		}else if(myList[x] == 1000000){
			sum+= myList[x]
		}else if(myList[x] == 1000000000){
			sum+= myList[x]
		}
	}
	sum = sum + number
	if(myList.size() == 1 && magsFound.size() == 0){
		sum = myList[0]
	}
}
//format the output
if(sum.getClass().equals(Double.class)){
	sum = String.format("%.0f",sum)
}
balance = sum
sum = sum + "";
sum = Double.parseDouble(sum);
sum = "\$" + String.format("%,.0f", sum)

}catch(Exception e){
	println "caught the error"
}
println sum
