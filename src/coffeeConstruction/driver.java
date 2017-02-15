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
	public worker[] getWorker(equipmentTypes e) {
		return null;
	}
	//method pair equipment and workers
	
}

class equipment{
	//TODO
	//******************
	//variable name
	//variable cost
	//array shift restrictions
	//******************
	//method constructor
	//method check shift restrictions
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
	//method check timeCard and fill it if it is empty
	public boolean checkTimeCard(int day, int shift){
		return false;
	}
	//method check shift restrictions
	
}

class timeCard{
	//TODO
	//******************
	//array [day][shift]
	
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
	//variable length (number of shifts?)
	int numberOfShifts;
	//array shifts
	equipmentTypes[][] equipmentUnits;
	//equipment[] equipmentList;
	//******************
	//method constructor
	project(String name, int number) {
		this.projectName = name;
		this.numberOfShifts = number;
	}
	//method add equipment shift and sort its order
	public void addEquipment(equipmentTypes e, int numOfShifts, int phaseNum) {
		//check if the target phase exists
		if(phaseNum+1 > equipmentUnits.length) {
			//add a new phases so we have room for the equipment
			equipmentTypes[][] pushShifts = new equipmentTypes[phaseNum+1][];
			int i=0;
			for(;i<phaseNum;i++) {
				pushShifts[i] = equipmentUnits[i];
			}
			equipmentUnits = pushShifts;
			//add the new equipment to the phase
			equipmentTypes[] pushEquipment = new equipmentTypes[numOfShifts];
			for(i=0; i<numOfShifts; i++) {
				pushEquipment[i] = e;
			}
			equipmentUnits[phaseNum] = pushEquipment;
			
		}else {
			//add the equipment to the phase if that phase already exists
			equipmentTypes[] pushEquip = new equipmentTypes[equipmentUnits[phaseNum].length+1];
			int i=0;
			for(;i<equipmentUnits[phaseNum].length;i++) {
				pushEquip[i] = equipmentUnits[phaseNum][i];
			}
			pushEquip[i] = e;
			equipmentUnits[phaseNum] = pushEquip;
		}
	}
	
	public void removeShift(equipmentTypes e, int phase) {
		equipmentTypes[] popShift = new equipmentTypes[equipmentUnits.length-1];
		for(int i=0;i<popShift.length;i++) {
			if(e == equipmentUnits[phase][i]) continue;
			else popShift[i] = equipmentUnits[phase][i];
		}
		
		equipmentUnits[phase] = popShift;
	}
}

class foreman{
	//TODO
	//******************
	//keep track of teams each day
	constructionTeam[][] teams;
	//list of projects
	project[] projects;
	procurement proc;
	//******************
	//method constructor
	foreman(procurement proc, project[] p){
		this.proc = proc;
		this.projects = p;
	}
	
	//method request equipment
	public void requestEquipment() {
		//index for construction days
		int currentDay = 0;
		//determine if all equipment needs for a project are met
		boolean jobsScheduled = false;
		//we will break once all shifts in all projects are accounted for
		while(!jobsScheduled) {
			//cycle through all the projects the foreman is managing
			for (project p : projects) {
				//iterate over each phase of the projects
				for(int phase=0; phase<p.equipmentUnits.length;phase++) {
					//check if there are still needs for the current project if not we can move to the next project
					if (p.equipmentUnits[phase].length > 0) {
						//reset the loop control if the prevoius project was complete but this one is not
						jobsScheduled = false;
						//get a list of the needed quipment for this project
						equipmentTypes[] needs = p.equipmentUnits[phase];
						//request the equipment from procurement
						for(equipmentTypes e: needs) {
							//get the worker associated with the given equipment
							worker w[] = proc.getWorker(e);
							//check each worker of the type we are looking for
							for(worker currentWorker : w) {
							//loop over the 3 shifts of the day
								for (int shift=0; shift<3;shift++) {
									//check if the worker associated with the equipment is already working
									if(currentWorker.checkTimeCard(currentDay, shift)){
										//make sure the the equipment and worker can work for the given shit
										if(currentWorker.checkShiftRestrictions(shift) && e.checkShiftRestrictions(shift)) {
											//create a new construction team for the project if not yet created
											constructionTeam ct = checkTeam(p, currentDay);
											//add the worker to the team
											ct.addWorker(currentWorker, shift);
											//remove the requirement from the project as it is met
											p.removeShift(e, phase);
											//fill out the worker's timecard
											currentWorker.setTimeCard(currentDay, shift);
										}
									}
								}
							}
						}
					}else {
						jobsScheduled = true;
					}
				}
			}
			currentDay++;
		}
	}
	
	//determine if a construction team is created return it if it is or a new one if not
	private constructionTeam checkTeam(project p, int day) {
		for(constructionTeam ct : teams[day]) {
			if (ct.getProject() == p)
				return ct;
		}
		
		return new constructionTeam(p);
	}
	
	//method calculate cost per shift and cost per day
}

class constructionTeam{
	//TODO
	//******************
	//array workers
	private worker[][] shifts;
	//variable project
	private project project;
	//******************
	//method constructor
	constructionTeam(project p) {
		this.project = p;
	}
	
	public void setProject(project p) {
		this.project = p;
	}
	
	public void addWorker(worker w, int shiftNum) {
		if(shiftNum+1 > shifts.length) {
			worker[][] pushShifts = new worker[shiftNum+1][];
			for(int i=0; i<shifts.length; i++) {
				pushShifts[i] = shifts[i];
			}
		}else {
			worker[] pushWorkers = new worker[shifts[shiftNum].length+1];
			int i=0;
			for(; i<shifts[shiftNum].length;i++) {
				pushWorkers[i] = shifts[shiftNum][i];
			}
			pushWorkers[i] = w;
			shifts[shiftNum] = pushWorkers;
		}
	}
	
	public project getProject() {
		return this.project;
	}
	
}

enum equipmentTypes{
	BACKHOE,
	CRANE,
	CEMENTTRUCK,
	CARPENTRYTOOLS,
	ELECTRICALTOOLS,
	PLUMBINGTOOLS
}