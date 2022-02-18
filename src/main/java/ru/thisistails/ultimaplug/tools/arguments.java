package ru.thisistails.ultimaplug.tools;

public class arguments {

    public static String getMessageFromArgs(String[] args, int start) {
        String report_msg = "";

        for (int i = start; i < args.length; i++) {
            report_msg = report_msg + args[i] + " ";
        }

        return report_msg;
    }

}
