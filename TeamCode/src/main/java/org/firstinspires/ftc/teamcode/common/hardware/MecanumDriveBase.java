package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.common.CV.Location;
import org.firstinspires.ftc.teamcode.common.CV.saturationDetector;
import org.firstinspires.ftc.teamcode.common.RR.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.RR.trajectorysequence.TrajectorySequence;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;

import java.util.function.DoubleSupplier;

public class MecanumDriveBase extends Subsystem {
    private Pose2d StartPose;
    public SampleMecanumDrive drive;
    public MecanumDriveBase(@NonNull OpModeEX opModeEX, Pose2d startPose) {
        super(opModeEX);
        StartPose = startPose;
    }

    public TrajectorySequence leftTraj, midTraj, rightTraj;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(opModeEX.hardwareMap);
        drive.setPoseEstimate(StartPose);
        //setDefaultCommand(stop());
        if(Globals.ALLIANCE==Location.RED) {
            drive.setPoseEstimate(new Pose2d(15, -60, Math.toRadians(90)));
            leftTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(15, -34)).lineTo(new Vector2d(10, -34))
                    .build();
            midTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(16, -34, Math.toRadians(-90)))
                    .build();
            rightTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(12, -34, Math.toRadians(180))) //
                    .build();
        } else {
            drive.setPoseEstimate(new Pose2d());
            leftTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(15, -34)).lineTo(new Vector2d(10, -34))
                    .build();
            midTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(16, -34, Math.toRadians(-90)))
                    .build();
            rightTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .lineToLinearHeading(new Pose2d(12, -34, Math.toRadians(180))) //
                    .build();
        }
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
                    drive.setDrivePower(pose2d);
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
                    drive.setDrivePower(new Pose2d(0,0,0));
                })
                .setFinish(()->false);
    }
}
