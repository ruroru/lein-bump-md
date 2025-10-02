(defproject org.clojars.jj/bump-md  "1.1.1-SNAPSHOT"
  :description "A leiningen plugin to bump project version in README.md"
  :url "https://github.com/ruroru/lein-bump-md"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.3"]]

  :deploy-repositories [["clojars" {:url      "https://repo.clojars.org"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass}]]
  :profiles {:test {:dependencies [[mock-clj "0.2.1"]]
                    :resource-paths ["test/resources"]}}
  :plugins [[org.clojars.jj/bump "1.0.4"]
            [org.clojars.jj/bump-md "1.0.0-SNAPSHOT"]
            [org.clojars.jj/strict-check "1.0.2"]])
