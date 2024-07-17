package com.example.backend.model.entity.judge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JudgeResult {
    private String source_code;
    private int language_id;
    private String stdin;
    private String expected_output;
    private String stdout;
    private int status_id;
    private String created_at;
    private String finished_at;
    private String time;
    private int memory;
    private String stderr;
    private String token;
    private int number_of_runs;
    private String cpu_time_limit;
    private String cpu_extra_time;
    private String wall_time_limit;
    private int memory_limit;
    private int stack_limit;
    private int max_processes_and_or_threads;
    private boolean enable_per_process_and_thread_time_limit;
    private boolean enable_per_process_and_thread_memory_limit;
    private int max_file_size;
    private String compile_output;
    private int exit_code;
    private String exit_signal;
    private String message;
    private String wall_time;
    private String compiler_options;
    private String command_line_arguments;
    private boolean redirect_stderr_to_stdout;
    private String callback_url;
    private String additional_files;
    private boolean enable_network;
    private String post_execution_filesystem;
    private Status status;
    private Language language;
    private boolean processed;

    @Getter
    @Setter
    public static class Status {
        private int id;
        private String description;
    }

    @Getter
    @Setter
    public static class Language {
        private int id;
        private String name;
    }
}
