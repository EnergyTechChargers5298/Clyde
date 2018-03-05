package org.usfirst.frc.team5298.robot;

import org.usfirst.frc.team5298.robot.subsystems.Grabber;
import org.usfirst.frc.team5298.robot.subsystems.Lifter;
import org.usfirst.frc.team5298.robot.autonomous.StartLeft;
import org.usfirst.frc.team5298.robot.autonomous.StartMiddle;
import org.usfirst.frc.team5298.robot.autonomous.StartRight;
import org.usfirst.frc.team5298.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.HashMap;

public class Robot extends TimedRobot {
	
	public static OI oi;
	public static Drivetrain drivetrain;
	public static Grabber grabber;
	public static Lifter lifter;

	private String autoPositionSelected;
	private HashMap<char, CommandGroup>[] commandSelector;
	private SendableChooser<String> autoPosition;
	private Command autoCommand;
	
	String gameData;
	char scaleSide;
	
	private double autoStartTime;

    public void robotInit() {
		oi = new OI();
		drivetrain = new Drivetrain();
		grabber = new Grabber();
		lifter = new Lifter();
		
		commandSelector = Stream.generate(HashMap<char, CommandGroup>::new).limit(3).collect(Collectors.toList());
		commandSelector.get(0).put('L', new /*...*/);
		commandSelector.get(0).put('R', new /*...*/);
		commandSelector.get(1).put('L', new /*...*/);
		commandSelector.get(1).put('R', new /*...*/);
		commandSelector.get(2).put('L', new /*...*/);
		commandSelector.get(2).put('R', new /*...*/);

		/*
		autoPosition = new SendableChooser<String>();
		autoPosition.addDefault("Default Auto", "Start Left");
		autoPosition.addObject("Left Position", "Start Left");
		autoPosition.addObject("Middle Position", "Start Middle");
		autoPosition.addObject("Right Position", "Start Right");
		SmartDashboard.putData("Auto Position", autoPosition);
		*/
    }
	
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
		Timer.delay(0.2);

		dsInstance = DriverStation.getInstance();
		currentHash = commandSelector.get(dsInstance.getLocation() - 1);

    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	scaleSide = gameData.charAt(1);
    	
    	switch(autoPositionSelected) {
    		case "Start Right":
    			autoCommand = new StartRight(scaleSide);
    			break;
    			
    		case "Start Middle":
    			autoCommand = new StartMiddle(scaleSide);
    			break;
    			
    		case "Start Left":
    			autoCommand = new StartLeft(scaleSide);
    			break;
    	}
    	
    	autoCommand.start();
    	autoStartTime = Timer.getFPGATimestamp();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        if(Timer.getFPGATimestamp() - autoStartTime >= 15) {
        	autoCommand.cancel();
        }
    }

    public void teleopInit() {
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
    	Scheduler.getInstance().run();
    }
}