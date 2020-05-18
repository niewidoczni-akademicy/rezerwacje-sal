//custom react hook for forms
import { useState, useEffect } from "react";

const useForm = (initState, callback, validate) => {
  const [values, setValues] = useState(initState);
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setValues({
      ...values,
      [name]: value,
    });
  };

  const setState = (state) => {
    setValues(state);
  };

  // TODO: common useForm.jsx could be more generic
  const handleSubmit = async () => {
    setErrors(await validate(values));
    setIsSubmitting(true);
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      callback();
    }
  }, [errors]);

  return {
    handleChange,
    handleSubmit,
    values,
    errors,
    setState,
  };
};

export default useForm;
