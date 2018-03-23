package org.usfirst.frc.team5298.robot.commands;

import org.usfirst.frc.team5298.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Lift extends Command {

	double speed;
	
	public Lift(double speed) 
	{
		this.speed = speed;
		
		requires(Robot.grabber);

	}

	protected void initialize() {
		Robot.Lifter.LifterSet(speed);
	}

	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}