package badWordScanner;

public enum Sensitivity {
    NOFILTER("","", new String[][] {{""}}, new String[][] {{""}}), //Skips AI Completely

    ZERO_TOLERANCE(
            "1. Anything that insults individuals or groups" +
                    "2. Anything that threatens violence" +
                    "3. Anything that contains words considered to be swear words or expletives" +
                    "4. Swearing (even if it is not directed at any individual)" +
                    "5. Vulgar words and language" +
                    "6. Sexual harassment or extremely vulgar attacks against individuals" +
                    "7. HIDDEN/OBFUSCATED: Rules apply even if words are written backwards, spaced out (F u c k), leetspeak, or using symbols.", // NEU

            "1. Anything that does not meet the conditions ",

            new String[][] {
                    {
                            "VmVyZGFtbXQsIGljaCBrb21tZSB6dSBzcMOkdCB6dXIgQXJiZWl0Lg==",
                            "W3RydWVdIFZlcndlbmR1bmcgdm9uIFNjaGltcGZ3w7ZydGVybg=="
                    },
                    {
                            "R3V0ZW4gVGFnLCBpY2ggaMOkdHRlIGdlcm5lIGVpbmUgQXVza3VuZnQu",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "RGFzIHNpZWh0IGVjaHQga2Fja2UgYXVzLg==",
                            "W3RydWVdIFZ1bGfDpHJlIEF1c2RydWNrc3dlaXNl"
                    },
                    {
                            "RHUgYmlzdCBkb29mLg==",
                            "W3RydWVdIEJlbGVpZGlnZW5kZSBTcHJhY2hl"
                    },
                    {
                            "RGFzIGlzdCBTLmMuaC5lLmkuw58uZQ==",
                            "W3RydWVdIFZlcnN0ZWNrdGVzIFNjaGltcGZ3b3J0IGVya2FubnQ="
                    }
            },

            new String[][] {
                    {
                            "RGFtbiwgSSdtIGxhdGUgZm9yIHdvcmsu",
                            "W3RydWVdIFVzZSBvZiBzd2VhciB3b3Jkcw=="
                    },
                    {
                            "SGVsbG8sIEkgd291bGQgbGlrZSBzb21lIGluZm9ybWF0aW9uLg==",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "VGhhdCBsb29rcyByZWFsbHkgc2hpdHR5Lg==",
                            "W3RydWVdIFZ1bGdhciBleHByZXNzaW9u"
                    },
                    {
                            "WW91IGFyZSBzdHVwaWQu",
                            "W3RydWVdIEluc3VsdGluZyBsYW5ndWFnZQ=="
                    },
                    {
                            "VGhpcyBpcyBzLmguaS50Lg==",
                            "W3RydWVdIEhpZGRlbiBzd2VhciB3b3JkIGRldGVjdGVk"
                    }
            }

    ),

    PROFESSIONAL(
            "1. ANY insult against a person, no matter how ‘mild’" +
                    "2. Vulgar expressions are prohibited, even if they are not directed at anyone" +
                    "3. Aggressive commands (Shut up, Piss off)" +
                    "4. Racism, sexism, and threats" +
                    "5. HIDDEN/OBFUSCATED: Catch vulgarity or insults even if written backwards, spaced out, or replaced with numbers.", // NEU

            "1. Mild frustration words that are NOT foul language (shit, damn, crap)" +
                    "2. Words that describe errors or bad luck (bug, glitch, too bad)" +
                    "3. It must never be directed at a person",

            new String[][] {
                    {
                            "SGFsdCBkaWUgS2xhcHBlIHVuZCBow7ZyIHp1Lg==",
                            "W3RydWVdIEFnZ3Jlc3NpdmVyIEJlZmVobA=="
                    },
                    {
                            "U28gZWluIE1pc3QsIGRhcyBSZXplcHQgaGF0IG5pY2h0IGZ1bmt0aW9uaWVydC4=",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "V2FzIGbDvHIgZWluZSBTY2hlaWc3ZSBpc3QgZGFzIGRlbm4/",
                            "W3RydWVdIFZ1bGfDpHJlIEF1c2RydWNrc3dlaXNl"
                    },
                    {
                            "RHUgSS5kLmkuby50",
                            "W3RydWVdIEJlbGVpZGlndW5nIGdlZ2VuIGVpbmUgUGVyc29uIChWZXJzY2hsZWllcnQp"
                    },
                    {
                            "RHUgaGFzdCBqYSBrZWluZSBBaG51bmcsIGR1IEFuZsOkbmdlci4=",
                            "W3RydWVdIEJlbGVpZGlndW5nIGdlZ2VuIGVpbmUgUGVyc29u"
                    }
            },


            new String[][] {
                    {
                            "U2h1dCB1cCBhbmQgbGlzdGVuLg==",
                            "W3RydWVdIEFnZ3Jlc3NpdmUgY29tbWFuZA=="
                    },
                    {
                            "U3VjaCBjcmFwLCB0aGUgcmVjaXBlIGRpZG4ndCB3b3JrLg==",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "V2hhdCBraW5kIG9mIHNoaXQgaXMgdGhpcz8=",
                            "W3RydWVdIFZ1bGdhciBleHByZXNzaW9u"
                    },
                    {
                            "WW91IEkuZC5pLm8udA==",
                            "W3RydWVdIEluc3VsdCBhZ2FpbnN0IGEgcGVyc29uIChPYmZ1c2NhdGVkKQ=="
                    },
                    {
                            "WW91IGhhdmUgbm8gaWRlYSwgeW91IHJvb2tpZS4=",
                            "W3RydWVdIEluc3VsdCBhZ2FpbnN0IGEgcGVyc29u"
                    }
            }
    ),

    STANDARD(
            "1. Moderate to severe insults against individuals (e.g. asshole, idiot, bastard)" +
                    "2. Any form of racism or discrimination (zero tolerance)" +
                    "3. Sexual harassment or extremely vulgar attacks against individuals" +
                    "4. Serious threats of violence" +
                    "5. HIDDEN/OBFUSCATED: Explicitly check for hidden insults (e.g. 'A r s c h l o c h') or hidden racism. Tricks do not bypass the filter.", // NEU

            "1. General swearing that does not directly attack anyone (expression of frustration)" +
                    "2. Mild slang or trash talk (e.g., noob, loser, ez)" +
                    "3. Mild irony or fun, if recognizable",


            new String[][] {
                    {
                        "RGFzIGlzdCBkb2NoIHRvdGFsZXIgQmzDtmRzaW5uLCB3YXMgZHUgZGEgcmVkZXN0Lg==",
                        "W2ZhbHNlXQ=="
                    },
                    {
                        "RHUgSHVyZW5zb2huLCBmaWNrIGRlaW5lIE11dHRlci4=",
                        "W3RydWVdIFNjaHdlcmUgdnVsZ8OkcmUgQmVsZWlkaWd1bmcgZ2VnZW4gRmFtaWxpZW5hbmdlaMO2cmlnZQ=="
                    },
                    {
                        "U2NoZWnDn2UsIGljaCBoYWJlIG1laW5lbiBHZWxkYmV1dGVsIHZlcmdlc3NlbiE=",
                        "W2ZhbHNlXQ=="
                    },
                    {
                        "RHUgQS5yLnMuYy5oLmwuby5jLmg=",
                        "W3RydWVdIERpcmVrdGUgQmVsZWlkaWd1bmcgKFZlcnNjaGxlaWVydCk="
                    },
                    {
                        "QmlzdCBkdSBlaWdlbnRsaWNoIGJlaGluZGVydD8gTMO2c2NoIGRpY2gu",
                        "W3RydWVdIERpc2tyaW1pbmllcmVuZGUgQmVsZWlkaWd1bmcgYmV6w7xnbGljaCBCZWhpbmRlcnVuZw=="
                    }
            },



            new String[][] {
                    {
                            "VGhhdCdzIHRvdGFsIG5vbnNlbnNlIHdoYXQgeW91J3JlIHNheWluZy4=",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "WW91IHNvbiBvZiBhIGJpdGNoLCBmdWNrIHlvdXIgbW90aGVyLg==",
                            "W3RydWVdIFNldmVyZSB2dWxnYXIgaW5zdWx0IGFnYWluc3QgZmFtaWx5"
                    },
                    {
                            "U2hpdCwgSSBmb3Jnb3QgbXkgd2FsbGV0IQ==",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "WW91IEEucy5zLmhuby5sLmU=",
                            "W3RydWVdIERpcmVjdCBpbnN1bHQgKE9iZnVzY2F0ZWQp"
                    },
                    {
                            "QXJlIHlvdSByZXRhcmRlZD8gRGVsZXRlIHlvdXJzZWxmLg==",
                            "W3RydWVdIERpc2NyaW1pbmF0b3J5IGluc3VsdCByZWdhcmRpbmcgZGlzYWJpbGl0eQ=="
                    }
            }
    ),

    MINIMAL(
            "1. Very serious racial insults" +
                    "2. Extreme threats of violence" +
                    "3. HIDDEN/OBFUSCATED: Applies ONLY if the hidden text contains extreme racism or violence. Harmless hidden words are allowed.",

            "1. EVERYTHING ELSE" +
                    "2. Mild to moderate insults" +
                    "3. Very mild racial insults" +
                    "4. Mild to moderate threats of violence" +
                    "5. Anything that is not directed at individuals, no matter how bad",


            new String[][] {
                    {
                        "RHUgYmlzdCBzbyBlaW4gSWRpb3QsIGthbm5zdCBkdSBnYXIgbmljaHRzPw==",
                        "W2ZhbHNlXQ=="
                    },
                    {
                        "SWNoIHdlcmRlIGRpY2ggZmluZGVuIHVuZCBkZWluZSBGYW1pbGllIHVtYnJpbmdlbi4=",
                        "W3RydWVdIEV4dHJlbWUgQW5kcm9odW5nIHZvbiBwaHlzaXNjaGVyIEdld2FsdA=="
                    },
                    {
                        "VmVyZGFtbXRlIFNjaGVpw59lLCBtZWluIEF1dG8gaXN0IGthcHV0dCBnZWdhbmdlbi4=",
                        "W2ZhbHNlXQ=="
                    },
                    {
                        "RHUgc2NoZWnDnyBOLmUuZy5lLnI=",
                        "W3RydWVdIFNjaHdlcndpZWdlbmRlIHJhc3Npc3Rpc2NoZSBCZWxlaWRpZ3VuZyAoVmVyc2NobGVpZXJ0KQ=="
                    },
                    {
                        "RHUgQSByIHMgYyBo",
                        "W2ZhbHNlXQ=="
                    },
                    {
                        "UGFzcyBhdWYgd2FzIGR1IHNhZ3N0LCBzb25zdCBrcmllZ3N0IGR1IMOEcmdlci4=",
                        "W2ZhbHNlXQ=="
                    }
            },



            new String[][] {
                    {
                            "WW91IGFyZSBzdWNoIGFuIGlkaW90LCBjYW4ndCB5b3UgZG8gYW55dGhpbmc/",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "SSB3aWxsIGZpbmQgeW91IGFuZCBraWxsIHlvdXIgZmFtaWx5Lg==",
                            "W3RydWVdIEV4dHJlbWUgQW5kcm9odW5nIHZvbiBwaHlzaXNjaGVyIEdld2FsdA=="
                    },
                    {
                            "RnVja2luZyBoZWxsLCBteSBjYXIgYnJva2UgZG93bi4=",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "WW91IGZ1Y2tpbmcgTi5pLmcuZy5lLnI=",
                            "W3RydWVdIFNldmVyZSByYWNpc3QgaW5zdWx0IChPYmZ1c2NhdGVkKQ=="
                    },
                    {
                            "WW91IEEgcyBz",
                            "W2ZhbHNlXQ=="
                    },
                    {
                            "V2F0Y2ggd2hhdCB5b3Ugc2F5LCBvciB5b3UnbGwgYmUgaW4gdHJvdWJsZS4=",
                            "W2ZhbHNlXQ=="
                    }
            }
    );

    private final String conditions;
    private final String exceptions;

    //All examples are encoded in Base64, so nothing can detect them because they contain slurs
    private final String[][] exampleDE;
    private final String[][] exampleEN;


    Sensitivity(String bedingung, String ausnahme, String[][] exampleDE,  String[][] exampleEN) {
        this.conditions = bedingung;
        this.exceptions = ausnahme;
        this.exampleDE = exampleDE;
        this.exampleEN = exampleEN;
    }

    public String getConditions() {
        return conditions;
    }
    public String getExceptions() {
        return exceptions;
    }
    public String[][] getExampleDE() {
        return exampleDE;
    }
    public String[][] getExampleEN() {
        return exampleEN;
    }
}

