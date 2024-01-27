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
                        drive.trajectorySequenceBuilder(new Pose2d(12.00, -63.00, Math.toRadians(-90.00)))
//                                .addDisplacementMarker(0,()->{
//                                    intake.setIntakePower(()->-0.5).queue();
//                                })
//                                .addTemporalMarker(2,()->{
//                                    intake.setIntakePower(()->0).queue();
//                                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
//                                    deposit.manualControl(()->1);
//                                })
//                                .addTemporalMarker(3, ()->{
//                                    deposit.manualControl(()->0);
//                                })
//                                .addTemporalMarker(4,()->{
//                                    deposit.manualDepositControl(()->1);
//                                })
//                                .addTemporalMarker(6, ()->{
//                                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.FLOAT);
//                                    deposit.manualDepositControl(()->0);
//                                })
                                .setReversed(true)
                                .splineTo(new Vector2d(12.00, -35.00), Math.toRadians(90))
                                .setReversed(false)
                                .splineTo(new Vector2d(48.00, -36.00), Math.toRadians(0))
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(48.00, -61.00, Math.toRadians(0.00)), Math.toRadians(0.00))
                                .setReversed(false)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}