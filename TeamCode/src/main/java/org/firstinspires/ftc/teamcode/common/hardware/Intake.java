package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingDcMotorEX;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingServo;

import java.util.function.DoubleSupplier;

public class Intake extends Subsystem {
    DcMotorEx iMotor;
    Servo iLClaw, iRClaw;
    public Intake(@NonNull OpModeEX opModeEX) {
        super(opModeEX);
    }

    @Override
    public void init() {
        iMotor = new CachingDcMotorEX(opModeEX.hardwareMap.get(DcMotorEx.class, "intake"));
        iLClaw = new CachingServo(opModeEX.hardwareMap.get(Servo.class, "iLClaw"));
        iRClaw = new CachingServo(opModeEX.hardwareMap.get(Servo.class, "iRClaw"));
        //setDefaultCommand(stop());
    }

    @Override
    public void periodic() {

    }

    @Override
    public void defaultCommandExecute() {

    }

    @Override
    public void close() {

    }

    public Command setIntakePower(DoubleSupplier input){
        return new LambdaCommand().setRequirements(this).setInterruptible(true).setExecute(
                ()-> iMotor.setPower(input.getAsDouble())
        ).setFinish(()->true);
    }

    public Command clawOpen(){
        return new LambdaCommand().setRequirements(this).setInterruptible(true).setExecute(
                ()-> {
                    iLClaw.setPosition(0);
                    iRClaw.setPosition(1);
                }
                ).setFinish(()->true);
    }

    public Command clawClose(){
        return new LambdaCommand().setRequirements(this).setInterruptible(true).setExecute(
                ()->{
                    iLClaw.setPosition(1);
                    iRClaw.setPosition(0);
                }
        ).setFinish(()->true);
    }
    public Command stop(){
        return new LambdaCommand().setRequirements(this).setInterruptible(false).setExecute(
                ()->{
                    iMotor.setPower(0);
                }
        );
    }
}
