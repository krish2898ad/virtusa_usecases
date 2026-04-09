import os
import re
from datetime import date

LOG_FILE = "server.log"

# Read the log file and store it in a list
def read_file(filepath):
    lines = []
    try:
        with open(filepath, "r") as log:
            for line in log:
                lines.append(line.strip()) 
    except:
        print("Can't able to find the file")

    print(f"No of lines in the file: {len(lines)}")
    return lines


# Find the patterns using regular expressions
def filter_critical_lines(lines):
    alerts   = []
    count  = {"CRITICAL": 0, "ERROR": 0, "FAILED LOGIN": 0}

    for line in lines:
        match = re.search(r'CRITICAL|ERROR|FAILED LOGIN', line)

        if match:
            alerts.append(line)
            if "CRITICAL" in line:
                count["CRITICAL"] += 1
            elif "FAILED LOGIN" in line:
                count["FAILED LOGIN"] += 1
            elif "ERROR" in line:
                count["ERROR"] += 1

    return alerts, count

# Create and write alerts data into the new security_alert file
def write_alert_report(alerts, count):
    today      = date.today()
    filename   = f"security_alert_{today}.txt"

    with open(filename, "w") as report:
        report.write("\n ---- OPSBOT SECURITY ALERT REPORT ----\n")
        report.write(f"Generated on: {today}\n")
        report.write(f"Source file:  {LOG_FILE}\n")

        # Error frequency summary
        report.write("\n--- SUMMARY (Error Frequency) ---\n")
        for error_type, error_count in count.items():
            report.write(f"{error_type}: {error_count} occurrences\n")
        report.write(f"Total alerts found: {len(alerts)}\n")

        # The actual alert lines
        report.write("\n--- DETAILED ALERT LOG ---\n\n")

        if not alerts:
            report.write("No critical events found. All systems normal.\n")
        else:
            for line in alerts:
                report.write(line + "\n")

    return filename

def main():
    print("\nOpsBot Log Automater\n")

    # Step 1 - Read the raw log
    lines = read_file(LOG_FILE)
    if not lines:
        return 

    # Step 2 - Filter dangerous lines
    alerts, count = filter_critical_lines(lines)

    # Print the frequency summary to the console
    print("\nError frequency count:")
    for error_type, error_count in count.items():
        print(f"  {error_type}: {error_count}")

    print(f"\n{len(alerts)} alert lines found out of {len(lines)} total lines.")

    # Step 3 - Write the report file
    output_filename = write_alert_report(alerts, count)
    print(f"\nAlert report saved as: '{output_filename}'")

    # Step 4  - get file size
    file_size_bytes = os.path.getsize(output_filename)
    print(f"File size confirmed: {file_size_bytes} bytes")


if __name__ == "__main__":
    main()
