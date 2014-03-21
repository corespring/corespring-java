## FieldValues

[FieldValues](/src/main/java/org/corespring/resource/FieldValues.java) represents a set of available fields for
filtering items within the Corespring API.


### CorespringClient methods

#### Get field values

    FieldValues fieldValues = client.getFieldValues();
    for (String gradeLevel : fieldValues.getGradeLevels()) {
        System.out.println(gradeLevel);             // 01, 02, 03, 04...
    }