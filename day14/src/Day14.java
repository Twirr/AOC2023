import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day14 {
    public static void main(String[] args) {
        List<char[]> inputData = new ArrayList<char[]>();
        File file = new File("input.txt");
        try(Scanner reader = new Scanner(file)){
            while(reader.hasNextLine()){
                var data = reader.nextLine();
                inputData.add(data.toCharArray());
            }
        }catch (FileNotFoundException e){
           System.out.println("Fail");
        }
        part1(inputData);
        part2(inputData);

    }

    private static void part1(List<char[]> inputData){
        moveAllNorth(inputData, Day14::moveNorth);
        System.out.println("Part1: "+calcLoad(inputData));
    }


    private static void part2(List<char[]> inputData){
        HashMap<Integer,List<Integer>> cache = new HashMap<Integer,List<Integer>>();
        moveAllWest(inputData, Day14::moveWest);
        moveAllSouth(inputData, Day14::moveSouth);
        moveAllEast(inputData, Day14::moveEast);
        //echoImage(inputData);
        var load = calcLoad(inputData);
        appendCache(cache,load,1);


        for(int i = 1; i < 10000; i++){
            moveAllNorth(inputData, Day14::moveNorth);
            moveAllWest(inputData, Day14::moveWest);
            moveAllSouth(inputData, Day14::moveSouth);
            moveAllEast(inputData, Day14::moveEast);
            load = calcLoad(inputData);
            appendCache(cache,load,i+1);
        }
        for(var entry : cache.entrySet()){
            if(entry.getValue().size() > 1){

                int lastValue = entry.getValue().get(entry.getValue().size()-1);
                int secondLastValue = entry.getValue().get(entry.getValue().size()-2);
                var loop = lastValue - secondLastValue;

                if((1000000000-lastValue) % loop == 0){
                    System.out.println("Part2: "+entry.getKey());
                }
            }
        }
    }
    private static void appendCache(HashMap<Integer,List<Integer>> cache, int load, int cycles){
        var list = cache.getOrDefault(load, new ArrayList<Integer>());
        list.add(cycles);
        cache.put(load,list);
    }
    private static int calcLoad(List<char[]> inputData){
        int load = inputData.size();
        int totalLoad = 0;
        for(var line : inputData){
            for(var c : line){
                if (c == 'O'){
                    totalLoad+=load;
                }
            }
            load--;
        }
        return totalLoad;
    }
    private static void moveAllNorth(List<char[]> inputData, NextMover mover){
        for(int i=0; i < inputData.size(); i++){
            for(int j=0; j < inputData.get(0).length; j++){
                var cell = inputData.get(i)[j];
                if(cell == 'O'){
                    moveNorth(inputData,i,j);
                }
            }
        }
    }
    private static void moveAllEast(List<char[]> inputData, NextMover mover){
        for(int j=inputData.get(0).length-1; j >=0; j--){
            for(int i=0; i < inputData.size(); i++){
                var cell = inputData.get(i)[j];
                if(cell == 'O'){
                    moveEast(inputData,i,j);
                }
            }
        }
    }
    private static void moveAllWest(List<char[]> inputData, NextMover mover){
        for(int j=0; j < inputData.get(0).length; j++){
            for(int i=0; i < inputData.size(); i++){
                var cell = inputData.get(i)[j];
                if(cell == 'O'){
                    moveWest(inputData,i,j);
                }
            }
        }
    }
    private static void moveAllSouth(List<char[]> inputData, NextMover mover){
        for(int i=inputData.size()-1; i >= 0; i--){
            for(int j=0; j < inputData.get(0).length; j++){
                var cell = inputData.get(i)[j];
                if(cell == 'O'){
                    moveSouth(inputData,i,j);
                }
            }
        }
    }
    private static void moveNorth(List<char[]> inputData, int x, int y){
        if(x == 0){
            return;
        }
        int nextX = x-1;
        move(inputData,x,y,nextX,y, Day14::moveNorth);
    }
    private static void moveSouth(List<char[]> inputData, int x, int y){
        if(x == inputData.size()-1){
            return;
        }
        int nextX = x+1;
        move(inputData,x,y,nextX,y, Day14::moveSouth);
    }
    private static void moveEast(List<char[]> inputData, int x, int y){
        if(y == inputData.get(0).length-1){
            return;
        }
        int nextY = y+1;
        move(inputData,x,y,x,nextY, Day14::moveEast);
    }
    private static void moveWest(List<char[]> inputData, int x, int y){
        if(y == 0){
            return;
        }
        int nextY = y-1;
        move(inputData,x,y,x,nextY, Day14::moveWest);
    }
    private static void move(List<char[]> inputData, int x, int y,int nextX, int nextY, NextMover nextMover){
        var next = inputData.get(nextX)[nextY];
        if(next == '.'){
            inputData.get(nextX)[nextY] = 'O';
            inputData.get(x)[y] = '.';
            nextMover.nextMove(inputData,nextX,nextY);
        }
    }
    private static void echoImage(List<char[]> inputData){
        for(var line: inputData){
            System.out.println(line);
        }
    }

    @FunctionalInterface
    private interface NextMover{
        void nextMove(List<char[]> inputData, int x, int y);
    }

}