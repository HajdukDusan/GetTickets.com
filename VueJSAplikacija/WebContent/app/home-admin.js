Vue.component("home-admin", {
  data: function () {
    return {
      cookie: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
  },

  template: `<div>
    
      <admin-nav></admin-nav>
    </div>`,
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
});
