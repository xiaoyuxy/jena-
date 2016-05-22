import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;


public class FamilyRelation {
	
	final String familyUri = "http://family/";
	final String relationshipUri = "http://purl.org/vocab/relationship/";
	Model model;
	// Create properties for the different types of relationship to represent
	Property childOf;
	Property parentOf;
	Property siblingOf; 
	Property spouseOf;
	Property grandparentOf;
	Property grandchildOf;
	
	
	
	public FamilyRelation(){
		
		this.model = ModelFactory.createDefaultModel();
		
		//TODO
		this.childOf = model.createProperty(relationshipUri,"childOf");
		this.parentOf = model.createProperty(relationshipUri,"parentOf");
		this.siblingOf = model.createProperty(relationshipUri,"siblingOf");
		this.spouseOf = model.createProperty(relationshipUri,"spouseOf");
		this.grandparentOf = model.createProperty(relationshipUri,"grandparentOf");
		this.grandchildOf = model.createProperty(relationshipUri,"grandchildOf");
		//this.prepareFamilyModel(model,relation);
	}
	
	public List<String> getRelation(String object, String relation){
		List<String> result = new ArrayList<String>();
		Resource obj = model.getResource(familyUri + object);
		relation += "Of";
		Property rel = model.getProperty(relationshipUri, relation);
		ResIterator subIter = model.listResourcesWithProperty(rel, obj);
		while(subIter.hasNext()){
			Resource person = (Resource)subIter.nextResource();
			result.add(person.getLocalName());
		}
		return result;
	}
	
	public void addRelation(String r){
		String[] relation = r.split(" ");
		this.prepareFamilyModel(model,relation);
	}
	
	private void prepareFamilyModel(Model model,String[] relation){
		// Create a Resource for each family member, identified by their URI
		
		Resource person1 = model.createResource(familyUri+relation[0]);
		Resource person2 = model.createResource(familyUri+relation[4]);
		// Add property of each family member
		if(relation[2].equals("parent"))
			person1.addProperty(parentOf,person2);
		if(relation[2].equals("child"))
			person1.addProperty(childOf, person2);
		if(relation[2].equals("sibling"))
			person1.addProperty(siblingOf,person2);
		if(relation[2].equals("spouse"))
			person1.addProperty(spouseOf, person2);
		if(relation[2].equals("grandparent"))
			person1.addProperty(grandparentOf,person2);
		if(relation[2].equals("grandchild"))
			person1.addProperty(grandchildOf, person2);
		
		/*Resource adam = model.createResource(familyUri+"adam");
		Resource beth = model.createResource(familyUri+"beth");
		Resource chuck = model.createResource(familyUri+"chuck");
		Resource dotty = model.createResource(familyUri+"dotty");
		Resource edward = model.createResource(familyUri + "edward");
		Resource fran = model.createResource(familyUri+"fran");
		Resource greg = model.createResource(familyUri+"greg");
		Resource hamet = model.createResource(familyUri + "hamet");
		*/	
		// Add properties to adam describing relationships to other family members
		//TODO add more relation
		/*
		adam.addProperty(siblingOf,beth);
		adam.addProperty(spouseOf,dotty);
		adam.addProperty(parentOf,edward);
		adam.addProperty(parentOf,fran);
		adam.addProperty(grandparentOf, hamet);
		beth.addProperty(siblingOf, adam);
		beth.addProperty(spouseOf, chuck);
		chuck.addProperty(spouseOf, beth);
		dotty.addProperty(spouseOf, adam);
		dotty.addProperty(parentOf, edward);
		dotty.addProperty(parentOf,fran);
		dotty.addProperty(grandparentOf,hamet);
		edward.addProperty(childOf, adam);
		edward.addProperty(childOf, dotty);
		edward.addProperty(siblingOf, fran);
		fran.addProperty(childOf,adam);
		fran.addProperty(childOf,dotty);
		fran.addProperty(spouseOf, greg);
		fran.addProperty(parentOf, hamet);
		greg.addProperty(spouseOf, fran);
		greg.addProperty(parentOf, hamet);
		hamet.addProperty(childOf, fran);
		hamet.addProperty(childOf, greg);
		hamet.addProperty(grandchildOf, adam);
		hamet.addProperty(grandchildOf, dotty);
		*/
	}
	
	
	
	public String getSpouseOf(String name){
		Resource spouse = model.getResource(familyUri + name);
		// List everyone in the model who has a child:
		ResIterator spouseIter = model.listSubjectsWithProperty(spouseOf, spouse);
		// Because subjects of statements are Resources, the method returned a ResIterator
		while (spouseIter.hasNext()) {
		  // ResIterator has a typed nextResource() method
			return spouseIter.nextResource().getLocalName();
		}
		
		NodeIterator spouseIter2 = model.listObjectsOfProperty(spouse, spouseOf);
		while(spouseIter2.hasNext()){
			return ((Resource) spouseIter2.nextNode()).getLocalName();
		}
		
		return null;
	}
	
	public ArrayList<String> getParentOf(String name){
		ArrayList<String> parents = new ArrayList<String>();
		Resource child = model.getResource(familyUri + name);
		// List everyone in the model who has a child:
		ResIterator parentsIter = model.listSubjectsWithProperty(parentOf, child);
		// Because subjects of statements are Resources, the method returned a ResIterator
		while (parentsIter.hasNext()) {
		  // ResIterator has a typed nextResource() method
		  Resource person = parentsIter.nextResource();
		  parents.add(person.getLocalName());
		}
		
		//parent's spouse is also the parent
		if(parents.size() < 2){
			String spouse = getSpouseOf(parents.get(0));
			if(spouse != null){
				parents.add(spouse);
			}
		}
			
		return parents;
	}
	
	private ArrayList<String> getChildOf_impl(String name){
		ArrayList<String> children = new ArrayList<String>();
		Resource parent = model.getResource(familyUri + name);
		NodeIterator childrenIter = model.listObjectsOfProperty(parent, parentOf);
		while(childrenIter.hasNext()){
			Resource person = (Resource) childrenIter.nextNode();
			children.add(person.getLocalName());
		}
		return children;
	}
	
	public ArrayList<String> getChildOf(String name){
		ArrayList<String> children;
		//String spouse = getSpouseOf(name);
		children = getChildOf_impl(name);
		//children.addAll(getChildOf_impl(spouse));
		return children;
	}
	public ArrayList<String> getSiblingOf(String name){
		ArrayList<String> siblings = new ArrayList<String>();
		Resource sibling = model.getResource(familyUri + name);
		ResIterator siblingIter = model.listSubjectsWithProperty(siblingOf, sibling);
		while (siblingIter.hasNext()) {
			  // ResIterator has a typed nextResource() method
			  Resource person = siblingIter.nextResource();
			  siblings.add(person.getLocalName());
			}
		return siblings;
		
	}
	public ArrayList<String> getGrandparentOf(String name){
		ArrayList<String> Grandparents = new ArrayList<String>();
		Resource Grandparent = model.getResource(familyUri + name);
		ResIterator grandparentIter = model.listResourcesWithProperty(grandparentOf,Grandparent);
		while(grandparentIter.hasNext()){
			Resource person = grandparentIter.nextResource();
			Grandparents.add(person.getLocalName());
		}
		
		
		return Grandparents;
	}
	private ArrayList<String> getGrandchildOf_impl(String name){
		ArrayList<String> grandchildren = new ArrayList<String>();
		Resource grandparent = model.getResource(familyUri + name);
		ResIterator grandchildrenIter = model.listResourcesWithProperty(grandchildOf, grandparent);
		while(grandchildrenIter.hasNext()){
			Resource person = (Resource) grandchildrenIter.nextResource();
			grandchildren.add(person.getLocalName());
		}
		return grandchildren;
	} 
	public ArrayList<String> getGrandchildOf(String name){
		ArrayList<String> Grandchildren;
		//String spouse = getSpouseOf(name);
		Grandchildren = getGrandchildOf_impl(name);
		//Grandchildren.addAll(getGrandchildOf_impl(spouse));
		return Grandchildren;
	}
	
	

	
	
	
}