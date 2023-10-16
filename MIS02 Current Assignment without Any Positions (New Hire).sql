-- Use the ODS_HR database
USE ODS_HR;

-- Declare and set date variables
DECLARE @start_date AS DATE
DECLARE @end_date AS DATE
SET @start_date = '08/08/2022'
SET @end_date = '12/09/2022'

-- Common Table Expression (CTE) to gather relevant data
;WITH CTE AS (
    -- Select faculty information from ODS_ST.dbo.S85_COURSE_SEC_FACULTY
    SELECT DISTINCT
        CSF_FACULTY AS EMP_ID,
        LAST_NAME,
        FIRST_NAME,
        CSF_COURSE_SECTION
    FROM ODS_ST.dbo.S85_COURSE_SEC_FACULTY
    INNER JOIN ODS_ST.dbo.S85_SEC_STATUSES
        ON CSF_COURSE_SECTION = COURSE_SECTIONS_ID
        AND POS = 1
    INNER JOIN ODS_ST.DBO.S85_PERSON
        ON CSF_FACULTY = ID
    WHERE CSF_START_DATE < @end_date
        AND CSF_END_DATE > @start_date
        AND SEC_STATUS = 'A'
        AND CSF_TEACHING_ARRANGEMENT NOT LIKE 'V%'
    
    -- Union with campus advisor information from S85_CAMPUS_ORG_ADVISORS_ADVISOR_LOAD
    UNION
    
    SELECT DISTINCT
        CMPA_PERSON_ST_ID AS EMP_ID,
        LAST_NAME,
        FIRST_NAME,
        CMPA_CAMPUS_ORGS_ID
    FROM ODS_ST.dbo.S85_CAMPUS_ORG_ADVISORS_ADVISOR_LOAD
    INNER JOIN ODS_ST.DBO.S85_PERSON
        ON CMPA_PERSON_ST_ID = ID
    WHERE CMPA_START_DATES < @end_date
        AND CMPA_END_DATES > @start_date
)

-- Select the desired employee information from the CTE
SELECT
    EMP_ID,
    LAST_NAME,
    FIRST_NAME,
    CSF_COURSE_SECTION
FROM CTE
WHERE (
    -- Check if HRPER_ID exists in dbo.S85_HRPER
    SELECT HRPER_ID
    FROM dbo.S85_HRPER
    WHERE HRPER_ID = EMP_ID
) IS NULL
