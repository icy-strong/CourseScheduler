# College Course Scheduling Application
This project is a Java-based Course Scheduling application for a college, designed to streamline the scheduling process for both administrators and students. The application employs a GUI and utilizes the Derby database for efficient data management. The implementation follows robust Object-Oriented Design and Programming principles to ensure a modular and scalable solution.

## Features
### Admin Functions
- Add Semester: Incorporates a new semester into the database, identified by a unique name.
- Add Course: Adds a new course to the database, identified by the course code and description.
- Add Class: Includes functionality to add a new class to the database, identified by the semester, course code, and maximum number of students.
- Add Student: Adds a new student to the database, identified by a studentID, first name, and last name.
- Display Class List of Students: Displays all students scheduled or waitlisted for a specified class in the current semester, prioritizing scheduled students and maintaining waitlist order.
- Drop Student: Removes a student from the student list and all associated classes in all semesters. Automatically schedules the first student on the waitlist into any affected courses, with detailed change logs.
- Drop Class: Removes a specified course from the current semester, clearing all scheduled and waitlisted students. Displays detailed change logs.
### Student Functions
- Schedule Class: Enables students to schedule for a specified class in the current semester, with waitlist functionality if no seats are available.
- Display Schedule: Displays the current schedule of classes for a specified student in the current semester, indicating the course code and student's status (scheduled or waitlisted).
- Display Classes: Shows a complete list of classes for the current semester, providing course code, description, and available seats information.
- Drop Class: Removes a specified class from the student's schedule for the current semester, considering both scheduled and waitlisted scenarios. Automatically schedules the first student waiting for the class and provides detailed change logs.
