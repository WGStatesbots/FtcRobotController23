package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingDcMotorEX;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingServo;

import java.util.function.DoubleSupplier;

public class EndGame extends Subsystem {
    DcMotorEx hMotor;
    Servo hServo, pServo;
    public EndGame(@NonNull OpModeEX opModeEX) {
        super(opModeEX);
    }

    @Override
    public void init() {
        hMotor = new CachingDcMotorEX( opModeEX.hardwareMap.get(DcMotorEx.class, "hMotor"));
        hServo = new CachingServo(opModeEX.hardwareMap.get(Servo.class, "hServo"));
        pServo = new CachingServo(opModeEX.hardwareMap.get(Servo.class, "pServo"));

        setDefaultCommand(manualWinchControl(()->0));
    }

    @Override
    public void periodic() {

    }

    @Override
    public void defaultCommandExecute() {
    }

    @Override
    public void close() {
        hMotor.setPower(0);
    }

    public Command launchDrone(){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    pServo.setPosition(0);
                })
                .setFinish(()->false);
    }
    public Command readyHook(){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    hServo.setPosition(0);
                })
                .setFinish(()->false);
    }
    public Command manualWinchControl(DoubleSupplier controller){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    hMotor.setPower(controller.getAsDouble());
                })
                .setFinish(()->false);
    }

    public Command raiseRobot(){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(false)
                .setExecute(()-> {
                    hMotor.setPower(0.3);
                })
                .setFinish(() -> hMotor.getCurrent(CurrentUnit.AMPS)<5)
                .setEnd(aBoolean -> {
                    hMotor.setPower(0);
                    hMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                });
    }

    public  Command drop(){
        return  new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(false)
                .setExecute(()->hMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT))
        .setFinish(()->true);
    }

    public Command lowerHook(){
        double startTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS).startTime();
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(false)
                .setExecute(()->hServo.setPosition(0.4));
    }
}
