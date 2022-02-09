//Name:David Wang 	URID:31322379
//Lab section: MW 200-315 Gavet
//email: dwang56@u.rochester.edu

public class StreetMap {
	
	public static void main(String[] args) {
		
		Graph graph = new Graph();
	
		int argLength  = args.length;
		
		switch (argLength) { 
       
        case 2: 
        	graph.run(args[0], args[1], "", "", "");
        	break; 
   
        case 4: 
        	graph.run(args[0], "", args[1], args[2], args[3]);
        	break; 
        
        case 5: 
        	graph.run(args[0], args[1], args[2], args[3], args[4]);
        	break;
     
        default: 

        	break; 
        }
 
	}

}
