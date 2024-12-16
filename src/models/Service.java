package models;

public class Service {
    private String service;
    private String serviceDone;
    private String mechanicName;
    private String timeTaken;
    private double price;
    private String date;
	private String mechnicname;
	private int serviceid;
	private String carmodel;

    public int getServiceid() {
		return serviceid;
	}

	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}

	public Service(String service, String serviceDone, String mechanicName, String timeTaken, double price) {
        this.service = service;
        this.serviceDone = serviceDone;
        this.mechanicName = mechanicName;
        this.timeTaken = timeTaken;
        this.price = price;
    }

    public Service(int serviceid, String service, double price, String date, String mechnicname, String carmodel) {
        this.serviceid = serviceid;
    	this.service = service;
        this.price = price;
        this.date = date;
        this.setMechnicname(mechnicname);
        this.carmodel = carmodel;
    }

    // Getters and setters
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getServiceDone() { return serviceDone; }
    public void setServiceDone(String serviceDone) { this.serviceDone = serviceDone; }

    public String getMechanicName() { return mechanicName; }
    public void setMechanicName(String mechanicName) { this.mechanicName = mechanicName; }

    public String getTimeTaken() { return timeTaken; }
    public void setTimeTaken(String timeTaken) { this.timeTaken = timeTaken; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

	public String getMechnicname() {
		return mechnicname;
	}

	public void setMechnicname(String mechnicname) {
		this.mechnicname = mechnicname;
	}

	public String getCarmodel() {
		return carmodel;
	}

	public void setCarmodel(String carmodel) {
		this.carmodel = carmodel;
	}
}
