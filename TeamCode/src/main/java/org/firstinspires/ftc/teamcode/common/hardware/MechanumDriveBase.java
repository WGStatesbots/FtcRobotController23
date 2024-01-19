package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.RR.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.CV.Location;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.bindings.gamepadex.DomainSupplier;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;
import org.mercurialftc.mercurialftc.silversurfer.geometry.Pose2D;

import java.util.function.DoubleSupplier;

public class MechanumDriveBase extends Subsystem {
    private SampleMecanumDrive sampleMecanumDrive;
    public MechanumDriveBase(@NonNull OpModeEX opModeEX, Pose2D startPose) {
        super(opModeEX);
    }

    @Override
    public void init() {
        sampleMecanumDrive = new SampleMecanumDrive(opModeEX.hardwareMap);
        setDefaultCommand(stop());
    }

    @Override
    public void periodic() {

    }

    @Override
    public void defaultCommandExecute() {

    }

    @Override
    public void close() {
        stop();
    }

    public Command manualControl(Pose2d pose2d) {
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    sampleMecanumDrive.setDrivePower(pose2d);
                })
                .setFinish(()->false);
    }
    public Command manualControl(DoubleSupplier x, DoubleSupplier y, DoubleSupplier t) {
        return manualControl(new Pose2d(x.getAsDouble(), y.getAsDouble(),t.getAsDouble()));
    }
    public Command stop(){
        return new LambdaCommand()
                .setRequirements(this)
                .setInterruptible(true)
                .setExecute(()->{
                    sampleMecanumDrive.setDrivePower(new Pose2d(0,0,0));
                })
                .setFinish(()->false);
    }
}
