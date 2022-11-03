// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.revrobotics.CANSparkMax;
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private static final int ShooterID = 6; 
  private CANSparkMax m_shooterMotor;
  private final MotorController m_leftBackMotor = new Spark(5);
  private final MotorController m_leftFrontMotor = new Spark(4);
  private final MotorController m_rightBackMotor = new Spark(3);
  private final MotorController m_rightFrontMotor = new Spark(1);
  private final MotorController m_turrent_aim = new Spark(2); // check
  private final MotorController m_revMotor = new Spark(0);
  private final MotorController m_intakeMotor = new Spark(6);
  private final MotorController m_HopperMotor = new Spark(7);
  private RobotContainer m_robotContainer;
  public final DifferentialDrive left = new DifferentialDrive(m_rightBackMotor, m_leftBackMotor);
  public final DifferentialDrive right = new DifferentialDrive(m_rightFrontMotor, m_leftFrontMotor);
  private final XboxController m_stick = new XboxController(0);
  private final XboxController m_stick2 = new XboxController(1);


  @Override
  public void robotInit() {

    m_robotContainer = new RobotContainer();
    m_shooterMotor = new CANSparkMax(ShooterID, MotorType.kBrushless);
  }




  @Override
  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    left.arcadeDrive(m_stick.getRawAxis(4) * .6F, m_stick.getRawAxis(1) * .7F);
    right.arcadeDrive(m_stick.getRawAxis(4) * .6F, m_stick.getRawAxis(1) * .7F);
    //Intake Motor
    if(m_stick.getRawButtonPressed(6)){
      m_intakeMotor.set(-1);
    }else if(m_stick.getRawButtonReleased(6)){
      m_intakeMotor.set(0);
    }else if(m_stick.getRawButtonPressed(5)){
      m_intakeMotor.set(1);
    }else if(m_stick.getRawButtonReleased(5)){
      m_intakeMotor.set(0);
    }
    
    double intake_value = m_stick2.getRawAxis(4);
    m_HopperMotor.set(intake_value*.5);

    if (m_stick2.getRawAxis(3) > .7f){
      m_shooterMotor.set(-1);
    }else{
      m_shooterMotor.set(0);
    }
    if(m_stick2.getRawButtonPressed(1)){
      m_revMotor.set(1);
    }else if(m_stick2.getRawButtonReleased(1)){
      m_revMotor.set(0);
    }
    if(m_stick2.getRawButtonPressed(2)){
      m_revMotor.set(-1);
    }else if(m_stick2.getRawButtonReleased(2)){
      m_revMotor.set(0);
    }

   
    double turing_value = m_stick2.getRawAxis(0);
    m_turrent_aim.set(turing_value*0.5);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
