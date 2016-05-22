import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main (String args[]) throws Exception{
		
		//FamilyRelation fr = new FamilyRelation();
		
		System.out.println("Enter your relation:(For example:Hamet is grandchild of Adam;Hamet is child of Greg)");
		Scanner input = new Scanner(System.in);
		String rel = input.nextLine();
		FamilyRelation fr = new FamilyRelation();
		for(String r:rel.split(";")){
			fr.addRelation(r);
		}
		
		
		while(true){
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter query information:(For example:grandchild of Adam) ");
            String info = reader.nextLine();
            String[] query = info.split(" ");
            List<String> result = fr.getRelation(query[2], query[0]);
            System.out.println(result);
            /*
            ArrayList<String> qu = new ArrayList<String>();
            for(String in:inf.split(" ")){
            	qu.add(in);
            }
            if(qu.get(0).equals("Parent")){
            	ArrayList<String> parents = fr.getParentOf(qu.get(2));
				System.out.println(parents);
            }
            if(qu.get(0).equals("Child")){
            	ArrayList<String> children = fr.getChildOf(qu.get(2));
            	System.out.println(children);
            }
            if(qu.get(0).equals("Spouse")){
				String spouse = fr.getSpouseOf(qu.get(2));
				System.out.println("[" + spouse + "]");
			}
            if(qu.get(0).equals("Sibling")){
				ArrayList<String> sibling = fr.getSiblingOf(qu.get(2));
				System.out.println(sibling);
			}
            if(qu.get(0).equals("Grandparent")){
				ArrayList<String> grandparent = fr.getGrandparentOf(qu.get(2));
				System.out.println(grandparent);
			}
            if(qu.get(0).equals("Grandchild")){
				ArrayList<String> grandchild = fr.getGrandchildOf(qu.get(2));
				System.out.println(grandchild);
			}
            else{
            	System.out.println("Your enter is invalid. You can enter like this:Grandchild of adam");
            }
            */
            
		}
		
	}
}
