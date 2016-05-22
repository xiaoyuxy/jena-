# jena-
How to use it:
1.	Download all the source code from the git
2.	Running the main function
4.	Enter your family relations.
    eg:Adam is parent of Edward;Adam is parent of Fran;Adam is sibling of beth
5.	Query your family tree as below:
    eg:parent of Adam

Background:

The Resource Description Framework (RDF) is a W3C recommendation for describing resources. In RDF, an arc is used to represent a property, labeled with the name. The name of a property is also a URI. Each arc in an RDF Model is called a statement. The resource is described by a statement. A statement has three parts:
•	the subject is the resource from which the arc leaves
•	the predicate is the property that labels the arc
•	the object is the resource or literal pointed to by the arc
Jena is a Java API for operations of RDF, such as store and query. Jena API makes it easy to save property in a data model and query from it.
Jena provides a lot APIs to query information from the data model. The two query API used in this project is:
* listSubjectsWithProperty()
* ListObjectsOfProperty() 

https://www.ibm.com/developerworks/library/j-jena/
https://jena.apache.org/tutorials/rdf_api.html

