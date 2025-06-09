(ns leiningen.bump-md
  (:require [clojure.string :as str])
  (:import (java.io File)))

(defn bump-md
  "I bump project version in md file"
  [project & rest]
  (when (.exists (File. "README.md"))
    (let [readme-md-content (slurp "README.md")
          version (:version project)]
      (spit "README.md" (str/replace readme-md-content (re-pattern (format "(?<=%s \\\")\\d+\\.\\d+\\.\\d+(\\-SNAPSHOT)?(?=\\\"\\])" (project :name))) version)))))
