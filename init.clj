#!/usr/bin/env bb

;; Check if the "./i3" directory exists
(def i3-dir "./i3")

(if (.exists (java.io.File. i3-dir))
  (do
    ;; Print the message if the directory exists
    (println "i3 exists papirrin, remember to put a good wallpaper in Downloads")

    ;; List the contents of the i3 directory
    (println "Contents of './i3':")
    (doseq [file (.listFiles (java.io.File. i3-dir))]
      (println (.getName file)))

    ;; Print the current date
    (println "\nCurrent date: " (java.time.LocalDate/now))

    ;; Get the current user's home directory
    (def current-user (System/getProperty "user.name"))
    (def home-dir (str "/home/" current-user))

    ;; Ask the user if they want to move the directory
    (println "\nDo you want to move the './i3' directory to " home-dir " and replace the current i3 config? (y/n)")

    ;; Read user input
    (let [user-input (clojure.string/trim (read-line))]
      (if (= user-input "y")
        (do
          ;; Create the target directory if it doesn't exist
          (let [target-dir (str home-dir "/.i3")]
            (if (not (.exists (java.io.File. target-dir)))
              (.mkdirs (java.io.File. target-dir)))

          ;; Move the directory
          (println "\nMoving './i3' to " target-dir "...")
          (clojure.java.io/copy (java.io.File. i3-dir) (java.io.File. target-dir))

          ;; Optionally, delete the original directory if needed
          ;; (clojure.java.io/delete-file (java.io.File. i3-dir))

          (println "Directory moved successfully!")
          (println "You can now replace the current i3 config and later add your Emacs configuration."))

        (println "\nOperation canceled."))
    )
  )
  (println "The './i3' directory does not exist.")))
