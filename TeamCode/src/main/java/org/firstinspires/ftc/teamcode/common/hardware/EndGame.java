package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.outoftheboxrobotics.photoncore.hardware.motor.PhotonDcMotor;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;

import java.util.function.DoubleSupplier;

public class EndGame extends Subsystem {
    PhotonDcMotor hMotor;
    PhotonServo hServo, pServo;
    public EndGame(@NonNull OpModeEX opModeEX) {
        super(opModeEX);
    }

    @Override
    public void init() {
        hMotor = opModeEX.hardwareMap.get(PhotonDcMotor.class, "hMotor");
        hServo = opModeEX.hardwareMap.get(PhotonServo.class, "hServo");
        pServo = opModeEX.hardwareMap.get(PhotonServo.class, "pServo");
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
                    pServo.setPosition(1);
                })
                .setFinish(()->false);
    }
    public Command readyHook(){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    hServo.setPosition(1);
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
                .setFinish(() -> hMotor.getCorrectedCurrent(CurrentUnit.AMPS)<5)
                .setEnd(aBoolean -> hMotor.setPower(0));
    }

    public Command lowerHook(){
        double startTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS).startTime();
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(false)
                .setExecute(()->hServo.setPosition(0))
                .setFinish(()-> (new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS).startTime()-startTime)>500)
                .setEnd(aBoolean -> hServo.setPosition(1));
    }
}
