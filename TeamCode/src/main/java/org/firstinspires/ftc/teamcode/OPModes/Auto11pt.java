package org.firstinspires.ftc.teamcode.OPModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.common.RR.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.RR.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.Intake;
import org.firstinspires.ftc.teamcode.common.hardware.MecanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;

public class Auto11pt extends OpModeEX {
    private Deposit deposit;
    private MecanumDriveBase mecanumDriveBase;
    private EndGame endGame;
    private Intake intake;
    private TrajectorySequence traj;
    @Override
    public void registerSubsystems() {
        deposit= new Deposit(this);
        mecanumDriveBase = new MecanumDriveBase(this, new Pose2d());
        endGame = new EndGame(this);
        intake = new Intake(this);
    }

    @Override
    public void initEX() {
        traj = mecanumDriveBase.drive.trajectorySequenceBuilder(new Pose2d()).lineTo(new Vector2d(0,36)).lineTo(new Vector2d(0,30)).build();
    }

    @Override
    public void registerBindings() {

    }

    @Override
    public void init_loopEX() {

    }

    @Override
    public void startEX() {
        mecanumDriveBase.drive.followTrajectorySequence(traj);
    }

    @Override
    public void loopEX() {

    }

    @Override
    public void stopEX() {

    }
}
