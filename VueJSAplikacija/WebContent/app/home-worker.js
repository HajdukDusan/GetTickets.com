Vue.component("home-worker", {
  data: function () {
    return {
      cookie: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
  },

  template: `<div>
    
      <worker-nav></worker-nav>
    </div>`,
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
});
