package scoring

abstract class ScoringCalculateClass extends Script {
    def version = { x ->
        binding.setVariable("version", x)
    }

    def scoring = { String x ->
        binding.setVariable("scoring", new BigDecimal(x))
    }

    def result (Closure conditionClosure) {
        def result = conditionClosure.call(binding.getVariable("scoring"))
        binding.setVariable("result", result)
    }

    class ValueSpec {
        def x
        BigDecimal result
        def conditionString
        def isMissing

        ValueSpec(x) {
            this.x = x
        }

        def when(Closure conditionClosure) {
            [then: { String what ->
                if (isMissing)
                    return
                def condition = conditionClosure.call()
                if (condition) {
                    if (conditionString != null) {
                        throw new IllegalStateException("Invalid rule found with two matches of '${x}' ${what} in conditions ${conditionString} and ${getConditionString(conditionClosure)}")
                    }
                    conditionString = getConditionString(conditionClosure)
                    result = new BigDecimal(what)
                }
            }]
        }

        String getConditionString(closure) {
            def result = closure.metaClass.classNode.getDeclaredMethods("doCall")[0].code.text
                    .replace("{ return ", "")
            result.substring(0, result.length() - 1)
        }

        def otherwise(String what) {
            if (result == null) {
                result = new BigDecimal(what)
                conditionString = "otherwise"
            }
        }


        public boolean missing(x) {
            isMissing = (x == null)
        }

        @Override
        public String toString() {
            return "{ x=" + x +
                    ", value=" + result +
                    ", condition=" + conditionString +
                    '}';
        }
    }

    class RuleSpec {
        String name
        BigDecimal multiplier
        def x
        def xName
        def binding
        ValueSpec value


        RuleSpec(binding) {
            this.binding = binding
        }

        void name(String name) {
            this.name = name
        }

        void multiplier(BigDecimal multiplier) {
            this.multiplier = multiplier
        }

        void multiplier(String multiplier) {
            this.multiplier = new BigDecimal(multiplier)
        }

        void x(xName) {
            this.xName = xName
            def params = binding.getVariable("scoringParams")
            this.x = params."${xName}"
        }

        void value(Closure valueClosure) {
            def valueSpec = new ValueSpec(x)
            def code = valueClosure.rehydrate(valueSpec, this, this)
            code.resolveStrategy = Closure.DELEGATE_ONLY
            code()
            value = valueSpec
        }

        String debug() {
            println this
        }


        @Override
        public String toString() {
            return """\
Rule{
    name='$name',
    multiplier=$multiplier,
    x=$x,
    xName=$xName,
    value=$value
}"""
        }
    }

    def rule(Closure cl) {
        def rule = new RuleSpec(this.binding)
        def code = cl.rehydrate(rule, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        BigDecimal scoring = binding.getVariable("scoring");
        binding.setVariable("scoring", scoring.add(rule.multiplier.multiply(rule.value.result)));
        rule.debug()
    }
}

println binding.getVariable("scoring")

