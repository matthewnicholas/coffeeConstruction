package coffeeConstruction;

/***
 * 
 * @author MATTHEW NICHOLAS
 * @author YAMAAN SHAIKH
 * @author CHAU NGUYEN
 *
 */

class diver{
	public static void main(String args[]) {
		//TODO
		System.out.println("Diver");
	}
}

/***
 * 
 * @author 
 *
 */

class procurement {
	//TODO
	//******************
	//variable equipment
	//variable workers
	//******************
	//method constructor
	//method provide requested workers and equipment
	//method pair equipment and workers
	
}

class equipment{
	//TODO
	//******************
	//variable name
	//variable cost
	//variable minimum number required
	//array shift restrictions
	//******************
	//method constructor
}

class worker{
	//TODO
	//******************
	//variable name
	//variable salary
	//variable equipment
	//variable timeCard
	//array shift restrictions
	//******************
	//method constructor
	//method check timeCard
	
}

class timeCard{
	//TODO
	//******************
	//array work days
	//array shifts (maybe an enum)
	
}


/*********************************************************8
 * 
 * @author Matthew Nicholas
 *
 */

class project{
	//******************
	//variable name
	String projectName;
	//variable type
	int projectType;
	//variable length (number of shifts?)
	int numberOfShifts;
	//array shifts
	shift[] projectShifts = new shift[numberOfShifts];
	//******************
	//method constructor
	project(String name, int type, int number) {
		this.projectName = name;
		this.projectType = type;
		this.numberOfShifts = number;
	}
	//method add equipment shift and sort its order
	public void addEquipment(equipment e, shift s, int order) {
		//TODO
	}
}

class foreman{
	//TODO
	//******************
	//variable project
	project currentProject;
	//variable team
	constructionTeam team;
	//variable cost per shift
	int costPerShift;
	//variable number of construction days
	int constructionDays;
	//variable project cost
	int projectCost;
	
	procurement proc;
	//******************
	//method constructor
	foreman(procurement p){
		this.proc = p;
	}
	//method request equipment
	public void requestEquipment(String type) {
		
	}
	//method request worker
	//method assign workers to team
	//method create schedule based on workers and equipment order needs
	//method calculate cost per shift and cost per day
}

class constructionTeam{
	//TODO
	//******************
	//array workers
	//variable project
	//******************
	//method constructor
	//method assign workers
	
}

class shift{
	//TODO
	//******************
	//variable shift time morning, day or night
	int shiftNum; //1-morning 2-day 3-night
	//array workers/equipment in shift
	equipment[] shiftEquipment = new equipment[10];
	
	shift(int num, equipment[] workers) {
		this.shiftNum = num;
		this.shiftEquipment = workers;
	}
}