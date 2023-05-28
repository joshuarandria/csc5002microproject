package vlibtour.vlibtour_visit_emulation_server;

import vlibtour.vlibtour_common.Position;

public interface VisitEmulationInterface {
	/**
	 * to write.
	 * 
	 * @param user to write.
	 * @return to write.
	 */
	Position getCurrentPosition(String user);
	/**
	 * to write.
	 * @param user to write.
	 * @return to write.
	 */
	Position getNextPOIPosition(String user);
	/**
	 * to write.
	 * @param user to write.
	 * @return to write.
	 */
	Position stepInCurrentPath(String user);
	/**
	 * to write.
	 * @param user to write.
	 * @return to write.
	 */
	Position stepsInVisit(String user);	
}
