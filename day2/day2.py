import re

file = open("input.txt", "r")

lines = file.readlines()

count = 0
power = 0
for line in lines:
    fail = False
    game = int(re.search("Game ([\d]+)",line).group(1));
    
    greens = [int(numeric_string) for numeric_string in re.findall("([\d]+) green",line)]
    largestGreen = 0
    for green in greens:
        if green > 13:
            fail = True
        if green > largestGreen:
            largestGreen = green
    
    blues = [int(numeric_string) for numeric_string in re.findall("([\d]+) blue",line)]
    
    largestBlue = 0
    for blue in blues:
        if blue > 14:
            fail = True
        if blue > largestBlue:
            largestBlue = blue
    
    reds = [int(numeric_string) for numeric_string in re.findall("([\d]+) red",line)]
    largestRed = 0
    for red in reds:
        if red > 12:
            fail = True
        if red > largestRed:
            largestRed = red
            
    if not fail:
        count += game;
            
    power += largestGreen*largestBlue*largestRed;

print(count)
print(power)