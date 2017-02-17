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
 * @author Yamaan Shaikh
 *
 */

class procurement {
    	//TODO
	//******************
	//variable equipment
    String equipment;
	//variable workers
    String worker;
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
    String equipmentName;
        //variable cost
    int cost;
	//array shift restrictions
    int[] shiftRestrictions = {0, 0, 0};
	//******************
	//method constructor
    equipment (String equipmentName, equipmentTypes c, int[] restrictions){
       
        switch (c)
       {
           case BACKHOE:
               cost = 150;
               break;
           case CRANE:
               cost = 2500;
               break;
           case CEMENTTRUCK:
               cost = 850;
               break;
           default:
               cost = 0;
           
       }
    }
	//method check shift restrictions
    public boolean checkShiftRestriction(int shiftRestriction)
    {
        return false;
    }
}

class worker{
	//TODO
	//******************
	//variable name
    String workerName;
    equipmentTypes type;
        //variable salary
    int salary;
	//variable equipment
    String equipment;
	//array shift restrictions
    int[] shiftRestrictions = {0, 0, 0};
	//******************
	//method constructor
        //parameters name and type
    worker (String name, equipmentTypes t){
        workerName = name;
        type = t;
       switch (t)
       {
           case CARPENTRYTOOLS:
               salary = 200;
               break;
           case ELECTRICALTOOLS:
               salary = 250;
               shiftRestrictions[2] = 1;
               break;
           case PLUMBINGTOOLS:
               salary = 70;
               break;
           default:
               salary = 0;
           
       }
    }
	//method check timeCard and fill it if it is empty
	public boolean checkTimeCard(int day, int shift){
		return false;
	}
        
        public boolean setTimeCard (int day, int shift){
            return false;
    }
	//method check shift restrictions
        public boolean checkShiftRestrictions(int shiftRestriction)
        {
            return false;
        }
	//method getSalary
	public int getSalary() 
        {
            return this.salary;
	}
}

class timeCard{
	//TODO
	//******************
	//array [day][shift]
    timeCard[][] timeCard;
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
	//variable cost chart per day per shift
		//		day shift cost
	int cost[][];
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
	
	public void setCost(int day, int shift, int cost) {
		int[][] pushCost = new int[day][shift];
		for(int d=0;d<this.cost.length;d++) {
			for(int s=0; s<this.cost[d].length;s++) {
				pushCost[d][s] = this.cost[d][s];
			}
		}
		pushCost[day][shift] = cost;
		this.cost = pushCost;
	}
	
	public int getCost(int day, int shift) {
		return cost[day][shift];
	}
	
	public int getCost(int day) {
		//return value
		int costPerDay = 0;
		//holder for if the project has started
		boolean projectStarted = false;
		//look at each shift of the day
		for(int shift=0; shift<3; shift++) {
			//add the shift cost to the day's cost
			costPerDay+=cost[day][shift];
			//if no one worked then the current days cost is 0 if there are people working then
			//there will be a cost and thus the project has started
			if (costPerDay > 0)
				projectStarted = true;
			
			//we also need to check to see if the project is just on hiatus
			//look back through the previous days and see if there were payments 
			//indicating that the project has started
			if(day > 0)
				for(int pastDay=day-1; pastDay>-1; pastDay--)
					if(cost[pastDay][shift] > 0)
						projectStarted = true;
			
		}
		//the daily fee addition is based on whether the project is started or not
		if(projectStarted)
			return costPerDay + 1000;
		else
			return costPerDay + 200;
	}
}

class foreman{
	//TODO
	//******************
	//keep track of teams each day
	constructionTeam[][] teams;
	//list of projects
	project[] projects;
	//passed procurment
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
						//reset the loop control if the previous project was complete but this one is not
						jobsScheduled = false;
						//get a list of the needed equipment for this project
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
										//make sure the the equipment and worker can work for the given shift
										if(currentWorker.checkShiftRestrictions(shift) && e.checkShiftRestrictions(shift)) {
											//check if they are a carpenter check previous shifts
											if(currentWorker.getEquipment().getType() == equipmentTypes.CARPENTRYTOOLS && shift > 0) {
												if(currentWorker.checkTimeCard(currentDay, shift-1)) {
													//create a new construction team for the project if not yet created
													constructionTeam ct = checkTeam(p, currentDay);
													//add the worker to the team
													ct.addWorker(currentWorker, shift);
													//remove the requirement from the project as it is met
													p.removeShift(e, phase);
													//fill out the worker's timecard
													currentWorker.setTimeCard(currentDay, shift);
												}
											}else {
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
								createCostSchedule(p, currentDay);
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
	private void createCostSchedule(project p, int day) {
		for (constructionTeam team : teams[day]) {
			if(team.getProject() == p) {
				//base cost for construction
				int cost = 0;
				//iterate over the 3 shifts in the day
				for(int shift=0;shift<3;shift++) {
					//get the team for that shift
					worker[] workers = team.getWorker(shift);
					//create the coordination per worker fee based on the number of workers
					float laborModifier = workers.length*.1f;
					for(worker w : workers) {
						//get the cost per worker
						cost += w.getSalary();
						//get the cost of that workers equipment
						cost += w.getEquipment().getCost();
						//electricians get a bonus if they have worked both available shifts in the day
						//check if this is the second shift and if so look to see if they worked the first one
						if(w.getEquipment().getType() == equipmentTypes.ELECTRICALTOOLS && shift == 1) {
							if(!w.checkTimeCard(day, shift-1)) {
								//if the worked both shifts give them a bonus
								cost+=100;
							}
						}
					}
					//add the coordination costs
					cost *= 1+laborModifier;
					//set the project cost
					p.setCost(day, shift, cost);
				}
			}
		}
	}
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
	
	public worker[] getWorker(int shift) {
		return shifts[shift];
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
