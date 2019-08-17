package sales;

import java.util.Date;

public class Sales {
	
	private boolean isSupervisor;

	public Date getEffectiveTo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getEffectiveFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSupervisor() {
		return isSupervisor;
	}

	public void setSupervisor(boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public boolean isEffectiveToday(Date today) {
		return today.after(this.getEffectiveTo())
				|| today.before(this.getEffectiveFrom());
	}
}
