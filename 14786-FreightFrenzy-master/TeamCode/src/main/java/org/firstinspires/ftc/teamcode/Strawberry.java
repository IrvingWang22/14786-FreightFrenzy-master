package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Strawberry TeleOP")
public class Strawberry extends LinearOpMode {

    // FIELD SPECIFIC DATA
    public static double STARTING_X = 16;
    public static double STARTING_Y = -42;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.update();

            // Read pose
            Pose2d poseEstimate = drive.getPoseEstimate();

            // Print pose to telemetry
            telemetry.addData("mode", currentMode);
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

            switch (currentMode) {
                case DRIVER_CONTROL:
                    //Drive
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y,
                                    -gamepad1.left_stick_x,
                                    -gamepad1.right_stick_x
                            )
                    );

                    //Intake
                    if (gamepad1.a) mech.runIntake(Mechanisms.intakeState.IN);
                    if (gamepad1.y) mech.runIntake(Mechanisms.intakeState.OUT);
                    if (gamepad1.b) mech.runIntake(Mechanisms.intakeState.OFF);

                    //Cube Control
                    if (gamepad1.right_bumper) mech.cubeHoldControl(Mechanisms.cubeHoldState.OPEN);
                    if (gamepad1.left_bumper) mech.cubeHoldControl(Mechanisms.cubeHoldState.CLOSE);

                    break;
                case AUTOMATIC_CONTROL:
                    // If x is pressed, we break out of the automatic following
                    if (gamepad1.start || gamepad1.start) {
                        drive.cancelFollowing();
                        currentMode = Mode.DRIVER_CONTROL;
                    }

                    // If drive finishes its task, cede control to the driver
                    if (!drive.isBusy()) {
                        currentMode = Mode.DRIVER_CONTROL;
                    }
                    break;
            }

        }
    }

}