(ns leiningen.bump-md-test
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.test :refer :all]
            [mock-clj.core :as mock]
            [leiningen.bump-md :as bump-md])
  (:import (java.io InputStream)
           (java.net URL)))


(deftest test-overwriting-file
  (mock/with-mock
    [spit nil
     slurp (fn [arg]
             (case arg
               "sub/project1/project.clj" "(defproject org.clojars.jj/bump-md1 )"
               "sub/project2/project.clj" "(defproject org.clojars.jj/bump-md2 )"
               "project.clj" "(defproject org.clojars.jj/bump-md )"
               "README.md" (String. (.readAllBytes ^InputStream (.openStream ^URL (io/resource "mock-readme.md"))))
               "empty"))]

    (bump-md/bump-md {:version "1.0.0" :sub ["sub/project1"
                                             "sub/project2"] :name "bump-md"})
    (is (= [(list "README.md" (str/replace "1.2.3\n1.2.3-SNAPSHOT\n[org.clojars.jj/bump-md2 \"1.0.0\"]\n[org.clojars.jj/bump-md \"1.0.0\"]\n"
                                           "\n"
                                           (System/lineSeparator)))]
           (mock/calls spit)))
    (is (= 1 (mock/call-count spit)))))
