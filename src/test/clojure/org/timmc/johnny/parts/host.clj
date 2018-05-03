(ns org.timmc.johnny.parts.host
  "Tests for host parsing, manipulation, and encoding."
  (:require [clojure.test :refer :all])
  (:import (org.timmc.johnny Urls)
           (org.timmc.johnny.parts
            Host RegNameHost IPv4Host IPv6Host IPvFutureHost)))

(deftest parsing-basic
  (testing "Parse & retrieve, check type and equality"
    (are [raw parsed] (let [h (.getHost (Urls/parse (str "https://" raw)))]
                        (and (= h parsed)
                             (= (.getRaw h) raw)))
         "example.com" (RegNameHost. "example.com")
         "1.2.3.4" (IPv4Host. 1 2 3 4)
         "1.2.3.4" (IPv4Host. (int-array [1 2 3 4]))
         "1.2.3.4" (IPv4Host. (int-array [1 2 3 4]) "1.2.3.4")
         "[2620:0:861:ed1a::1]" (IPv6Host. "2620:0:861:ed1a::1" nil
                                           "[2620:0:861:ed1a::1]")
         "[::1%25a%2Fb]" (IPv6Host. "::1" "a/b")
         "[2620::10.2.3.40]" (IPv6Host. "2620::10.2.3.40" nil
                                        "[2620::10.2.3.40]")
         "[v7.xyz]"
         (IPvFutureHost. "[v7.xyz]"))))

(deftest constructors
  (testing "don't accept null in various places"
    (is (thrown? NullPointerException (RegNameHost. nil)))
    (is (thrown? NullPointerException (IPv4Host. nil)))
    (is (thrown? NullPointerException (IPv4Host. (int-array [1 2 3 4]) nil)))
    (is (thrown? NullPointerException (IPv4Host. nil "1.2.3.4")))
    (is (thrown? NullPointerException (IPv6Host. nil)))
    (is (thrown? NullPointerException (IPv6Host. nil "eth0")))
    (is (thrown? NullPointerException (IPv6Host. nil "eth0" "[::1%25eth0]")))
    (is (thrown? NullPointerException (IPv6Host. "::1" "eth0" nil)))
    (is (thrown? NullPointerException (IPvFutureHost. nil)))))

(deftest ipv4-safety-copying
  (testing "IPv4Host copies array on input"
    (let [quad (int-array [1 2 3 4])
          ip-a (IPv4Host. quad)
          ip-b (IPv4Host. quad "1.2.3.4")]
      (aset quad 0 100)
      (is (= (vec quad) [100 2 3 4]))
      (is (= (vec (.getQuad ip-a)) [1 2 3 4]))
      (is (= (vec (.getQuad ip-b)) [1 2 3 4])))))
