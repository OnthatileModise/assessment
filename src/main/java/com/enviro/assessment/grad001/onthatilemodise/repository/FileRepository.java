package com.enviro.assessment.grad001.onthatilemodise.repository;

import com.enviro.assessment.grad001.onthatilemodise.model.FileModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * A Spring repository that provides methods to manipulate the "FILE_DATA" table in the database
 */
public interface FileRepository extends CrudRepository<FileModel , Long> {

    /**
     * Query the FILE_DATA table for FILE_DATA where UUID equals the passed parameter
     * @param fileUUID String uuid for file
     * @return String containing the file contents
     * **/
    @Query(
            """
            SELECT
                fm.fileData
            FROM
                FileModel fm
            WHERE
                fm.uuid = ?1
            """
    )String getFileContentsByFileUUID(String fileUUID);
}
