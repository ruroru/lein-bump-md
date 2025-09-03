(ns leiningen.bump-md
  (:require [clojure.string :as str])
  (:import (java.io File)))

(def ^:private regex #"(?<=defproject)(\s+(\w|\.|-)+\/)?(\w|\.|-)+(?=\s)")
(def ^:private readme-md "README.md")

(defn- find-project-name
  "Finds the first project name match in the given text."
  [file-path]
  (when-let [match (re-find regex (slurp file-path))]
    (clojure.string/trim (first match))))

(defn- replace-all-project-versions
  "Replaces version strings for all projects in the project list within readme content."
  [readme-content project-list new-version]
  (reduce (fn [content project-name]
            (str/replace content
                         (re-pattern (format "(?<=%s \\\")\\d+\\.\\d+\\.\\d+(\\-SNAPSHOT)?(?=\\\"\\])" project-name))
                         new-version))
          readme-content
          project-list))

(defn bump-md
  "I bump project version in md file"
  [project & _]
  (when (.exists (File. ^String readme-md))
    (let [readme-md-content (slurp readme-md)
          version (:version project)
          sub-projects (map (fn [v]
                              (find-project-name v))
                            (conj (map (fn [v]
                                         (format "%s/project.clj" v))
                                       (:sub project []))
                                  "project.clj"))]

      (spit readme-md (replace-all-project-versions readme-md-content sub-projects version)))))
