package edu.pdx.cs410J.whitlock;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  private final Gender gender;
  private final double gpa;
  private final List<String> classes;

  /**
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male", "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, Gender gender) {
    super(name);
    this.gender = gender;
    this.gpa = gpa;
    this.classes = classes;
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {                                                            
    return "This class is too much work";
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    return getName() + " has a GPA of " + this.gpa + " and is taking " + getClassesDescription();
  }

  private String getClassesDescription() {
    int numClasses = this.classes.size();
    StringBuilder sb = new StringBuilder().append(numClasses).append(" class");

    if (numClasses != 1) {
      sb.append("es");
    }
    if (numClasses > 0) {
      sb.append(": ");
    }
    if (numClasses == 1) {
      sb.append(this.classes.get(0));

    } else if (numClasses == 2) {
      sb.append(this.classes.get(0));
      sb.append(" and ");
      sb.append(this.classes.get(1));

    } else if (numClasses > 2) {
      for (int i = 0; i < numClasses; i++) {
        sb.append(this.classes.get(i));
        if (i <= numClasses - 2) {
          sb.append(", ");
        }
        if (i == numClasses - 2) {
          sb.append("and ");
        }
      }
    }
    sb.append(".");
    return sb.toString();
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    try {
      Student student = createStudentFrom(args);
      System.out.println(student);

    } catch (UnrecognizedGenderException | MissingCommandLineArguments e) {
      System.err.println(e.getMessage());
    }

  }

  @VisibleForTesting
  static Student createStudentFrom(String... args) throws MissingCommandLineArguments, UnrecognizedGenderException {
    if (args.length == 0) {
      throw new MissingCommandLineArguments("Missing command line arguments");

    } else if (args.length == 1) {
      throw new MissingCommandLineArguments("Missing gender");

    } else if (args.length == 2) {
      throw new MissingCommandLineArguments("Missing GPA");

    } else {
      String name = args[0];
      Gender gender = validateGender(args[1]);
      double gpa = Double.parseDouble(args[2]);

      ArrayList<String> classes = new ArrayList<>(Arrays.asList(args).subList(3, args.length));
      return new Student(name, classes, gpa, gender);
    }
  }

  @VisibleForTesting
  static Gender validateGender(String gender) throws UnrecognizedGenderException {
    if (gender.equalsIgnoreCase("other")) {
      return Gender.OTHER;

    } else if (gender.equalsIgnoreCase("female")) {
      return Gender.FEMALE;

    } else if (gender.equalsIgnoreCase("male")) {
      return Gender.MALE;
    }

    throw new UnrecognizedGenderException(gender);
  }

  public Gender getGender() {
    return this.gender;
  }

  public double getGpa() {
    return gpa;
  }

  @VisibleForTesting
  static class UnrecognizedGenderException extends Exception {
    private final String unrecognized;

    public UnrecognizedGenderException(String unrecognized) {
      this.unrecognized = unrecognized;
    }

    public String getUnrecognizedGender() {
      return this.unrecognized;
    }

    @Override
    public String getMessage() {
      return "Unrecognized gender: " + this.getUnrecognizedGender();
    }
  }

  @VisibleForTesting
  static class MissingCommandLineArguments extends Exception {
    public MissingCommandLineArguments(String message) {
      super(message);
    }
  }
}