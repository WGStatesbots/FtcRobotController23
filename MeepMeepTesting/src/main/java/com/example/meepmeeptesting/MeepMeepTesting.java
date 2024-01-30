package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(90.00)))
                                .addDisplacementMarker(0.4,0,()->{
//                                    intake.setIntakePower(()->-1).queue();
//                                    deposit.manualControl(()->-1);
                                })
                                .addDisplacementMarker(0.5,0,()->{
//                                    intake.setIntakePower(()->0).queue();
//                                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
//                                    deposit.manualControl(()->0);
                                })
                                .addDisplacementMarker(0.6,0,()->{
//                                    deposit.manualControl(()->0);
//                                    deposit.manualDepositControl(()->1);
                                })
                                .addDisplacementMarker(.85,0, ()->{
//                                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.FLOAT);
//                                    deposit.manualDepositControl(()->0);
                                })
                                .setReversed(true)
                                .splineTo(new Vector2d(35.00, 34.00), Math.toRadians(180))
                                .setReversed(false)
                                .splineTo(new Vector2d(48.00, 42.00), Math.toRadians(0))
                                .setReversed(true)
                                .waitSeconds(2)
                                .splineToConstantHeading(new Vector2d(42, 61), Math.toRadians(0.00))
                                .setReversed(true)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}