package com.trying.developing.taskarrangement.model.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by developing on 4/18/2018.
 */

public class TaskContract {

    public static final String AUTHORITY = "com.trying.developing.taskarrangement.model.contentprovider.TaskContentProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_Tasks = "tasks";

    public static final class TaskEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_Tasks).build();

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_Task_Member= "taskMember";
        public static final String COLUMN_Task_Name = "taskName";
        public static final String COLUMN_Task_Description = "taskDescription";
        public static final String COLUMN_Task_TEAM_LEADER = "teamLeader";

        public static final String CREATE_TABLE_TASK = "CREATE TABLE " +
                TaskEntry.TABLE_NAME + "(" +
                TaskEntry.COLUMN_Task_Member + " TEXT NOT NULL," +
                TaskEntry.COLUMN_Task_Name + " TEXT NOT NULL," +
                TaskEntry.COLUMN_Task_Description + " TEXT NOT NULL," +
                TaskEntry.COLUMN_Task_TEAM_LEADER + " TEXT NOT NULL" +
                ");";

    }

}
